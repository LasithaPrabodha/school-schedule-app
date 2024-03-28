package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentHomeBinding
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper
import com.capstoneprojectg8.schoolscheduleapp.models.CalendarData
import com.capstoneprojectg8.schoolscheduleapp.models.ClassSlot
import com.capstoneprojectg8.schoolscheduleapp.ui.assignments.ClassesAdapter
import java.util.Calendar
import java.util.Date
import java.util.Locale


class HomeFragment : Fragment(), CalendarAdapterDelegate, ClassesAdapterDelegate {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var classAdapter: ClassSlotsAdapter
    private val classViewModel: HomeViewModel by activityViewModels()
    private lateinit var calendarAdapter: CalendarAdapter

    private val calendarList = ArrayList<CalendarData>()
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private var mStartD: Date? = null
    private lateinit var classSlots: List<ClassSlot>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupCalendarRecyclerView()
        initCalendar()
        setupClassRecyclerView()

        classViewModel.getAllClassSlots().observe(viewLifecycleOwner) { classSlots ->
            this.classSlots = classSlots
        }

        return root
    }

    private fun setupClassRecyclerView() {
        classAdapter = ClassSlotsAdapter(
            this,
            requireContext(),
            onItemClicked = { onAddAssignmentClick(it) },
            emptyList(),
        )
        binding.rvHomeAssignments.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = classAdapter
        }

        val today = DateHelper.getToday()

        classViewModel.getAllClassSlots().observe(viewLifecycleOwner) { classSlot ->
            val filtered = classSlot.filter { it.date == today }
            classAdapter.updateData(filtered)
        }

    }

    private fun setupCalendarRecyclerView() {
        calendarAdapter = CalendarAdapter(this, arrayListOf())
        binding.apply {
            calendarView.setHasFixedSize(true)
            calendarView.adapter = calendarAdapter
        }
    }

    private fun initCalendar() {
        mStartD = Date()
        cal.time = Date()
        DateHelper.getDates(mStartD, calendarList, calendarAdapter, binding)
    }

    private fun onAddAssignmentClick(slot: ClassSlot) {
        val action = HomeFragmentDirections.actionNavigationHomeToAddNewAssignmentFragment(slot.id)
        findNavController().navigate(action)
    }


    override fun onSelect(calendarData: CalendarData, position: Int, day: TextView) {
        calendarList.forEachIndexed { index, calendarData ->
            calendarData.isSelected = index == position
        }

        calendarAdapter.updateData(calendarList)

        val selectedDate = calendarData.data
        val formattedDate = DateHelper.formatDate(selectedDate)

        val filtered = classSlots.filter { it.date == formattedDate }

        if (filtered.isEmpty()) {
            binding.emptyScheduleMsg.visibility = View.VISIBLE
        } else {
            binding.emptyScheduleMsg.visibility = View.GONE

        }

        classAdapter.updateData(filtered)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onExpandable(holder: ClassSlotsAdapter.ViewHolder, item: ClassSlot) {
        holder.itemView.findViewTreeLifecycleOwner()?.let { it1 ->
            classViewModel.getAssignmentListByClassId(item.id)
                .observe(it1) { assignments ->
                    val assignmentList = assignments ?: emptyList()

                    if (assignmentList.isEmpty()) {
                        holder.binding.textViewAssignments.text = "No Assignments"
                    } else {
                        holder.binding.textViewAssignments.text = "Assignments"
                    }

                    val assignmentAdapter =
                        AssignmentsAdapter(assignmentList, classViewModel, requireContext())
                    holder.binding.rvHomeAssignments.adapter = assignmentAdapter
                    assignmentAdapter.updateData(assignmentList)
                }
        }
    }
}