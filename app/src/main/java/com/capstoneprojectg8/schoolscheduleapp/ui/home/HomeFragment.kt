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
import com.capstoneprojectg8.schoolscheduleapp.models.ScheduleSlot
import com.capstoneprojectg8.schoolscheduleapp.ui.schedule.WeekTimelineAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var cellWidth: Int = 0
    private lateinit var weekTimelineAdapter: WeekTimelineAdapter

    private val binding get() = _binding!!

    private lateinit var classAdapter: ClassesAdapter
    private lateinit var classViewModel: HomeViewModel
    private lateinit var classesRepository: ClassesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        setCellWidth()

        val dayOfWeek: MutableList<Map<String, String>> = DateHelper.generateDaysOfTheWeek()
        weekTimelineAdapter = WeekTimelineAdapter(requireContext(), dayOfWeek, cellWidth)

        binding.dayList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            adapter = weekTimelineAdapter
        }

        setUpClassViewModel()
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
    private fun setCellWidth() {
        val displayMetrics = resources.displayMetrics

        var dpWidth = displayMetrics.widthPixels
        dpWidth -= resources.getDimension(R.dimen.grid_time_indicator_layout_width).toInt()
        cellWidth = dpWidth / 5
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
}