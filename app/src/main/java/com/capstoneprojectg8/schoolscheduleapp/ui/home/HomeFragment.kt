package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.database.ClassesDatabase
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentHomeBinding
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.models.CalendarData
import com.capstoneprojectg8.schoolscheduleapp.models.ScheduleSlot
import java.util.Calendar
import java.util.Date
import java.util.Locale


class HomeFragment : Fragment(), CalendarAdapter.CalendarInterface {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var classAdapter: ClassesAdapter
    private lateinit var classViewModel: HomeViewModel
    private lateinit var classesRepository: ClassesRepository
    private lateinit var calendarAdapter: CalendarAdapter

    private val calendarList = ArrayList<CalendarData>()
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private var mStartD: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setUpClassViewModel()
        setupCalendarRecyclerView()
        initCalendar()
        setupClassRecyclerView()

        return root
    }

    private fun setupClassRecyclerView() {
        classesRepository = ClassesRepository(ClassesDatabase(requireContext()))
        classAdapter = ClassesAdapter(requireContext(), onItemClicked = { onAddAssignmentClick(it) }, emptyList(), classViewModel)
        binding.rvAssignments.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = classAdapter
        }

        val today = DateHelper.getToday()

        classViewModel.getAllClassSlots().observe(viewLifecycleOwner) {classSlot ->
            val filtered = classSlot.filter { it.date == today  }
            classAdapter.updateData(filtered)
        }

    }

    private fun setupCalendarRecyclerView(){
        calendarAdapter = CalendarAdapter(this, arrayListOf())
        binding.apply {
            calendarView.setHasFixedSize(true)
            calendarView.adapter = calendarAdapter
        }
    }

    private fun initCalendar(){
        mStartD = Date()
        cal.time = Date()
        getDates()
    }


    private fun getDates() {
        val dateList = ArrayList<CalendarData>()
        val dates = ArrayList<Date>()
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        while(dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            dateList.add(CalendarData(monthCalendar.time))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        calendarList.clear()
        calendarList.addAll(dateList)
        calendarAdapter.updateData(dateList)

        for (item in dateList.indices) {
            if(dateList[item].data == mStartD) {
                calendarAdapter.setPosition(item)
                binding.calendarView.scrollToPosition(item)
            }
        }
    }

    private fun onAddAssignmentClick(classId: ScheduleSlot) {
        val action = HomeFragmentDirections.actionNavigationHomeToAddNewAssignmentFragment(classId.id)
        findNavController().navigate(action)
    }

    private fun setUpClassViewModel() {
        val classesRepository = ClassesRepository(ClassesDatabase(requireContext()))
        val viewModelProviderFactory = HomeViewModelFactory(requireActivity().application, classesRepository)
        classViewModel = ViewModelProvider(this, viewModelProviderFactory)[HomeViewModel::class.java]
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSelect(calendarData: CalendarData, position: Int, day: TextView) {
        calendarList.forEachIndexed { index, calendarData ->
            calendarData.isSelected = index == position
        }
        calendarAdapter.updateData(calendarList)
    }
}