package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentHomeBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.CalendarData
import com.capstoneprojectg8.schoolscheduleapp.models.ClassSlot
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper
import com.capstoneprojectg8.schoolscheduleapp.utils.ThemeHelper.isDarkModeEnabled
import kotlinx.coroutines.launch
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

        (activity as AppCompatActivity).window.statusBarColor =
            resources.getColor(R.color.background)

        (activity as AppCompatActivity?)!!.supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                resources.getColor(
                    R.color.background
                )
            )
        )


        setupCalendarRecyclerView()
        initCalendar()
        setupClassRecyclerView()

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
            adapter = classAdapter
        }

        val today = DateHelper.getToday()

        classViewModel.getAllClassSlots().observe(viewLifecycleOwner) { slots ->
            classSlots = slots

            val filtered = slots.filter { it.date == today }

            if (filtered.isEmpty()) {
                binding.emptyScheduleMsg.visibility = View.VISIBLE
            } else {
                binding.emptyScheduleMsg.visibility = View.GONE
            }
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
        val action = HomeFragmentDirections.actionNavigationHomeToAddNewAssignmentFragment(
            slot.id
        )
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
        (activity as AppCompatActivity).window.statusBarColor =
            resources.getColor(R.color.transparent)

        if (isDarkModeEnabled(requireContext())) {
            (activity as AppCompatActivity?)!!.supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    resources.getColor(
                        com.google.android.material.R.color.background_material_dark
                    )
                )
            )
        } else {
            (activity as AppCompatActivity?)!!.supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    resources.getColor(
                        com.google.android.material.R.color.design_default_color_background
                    )
                )
            )
        }


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
                        AssignmentsAdapter(
                            requireContext(),
                            assignmentList,
                            { assignment: Assignment ->
                                lifecycleScope.launch {
                                    classViewModel.editAssignment(assignment)
                                }
                            },
                            { assignment: Assignment ->
                                lifecycleScope.launch {
                                    classViewModel.deleteAssignment(assignment)
                                }
                            })
                    holder.binding.rvHomeAssignments.adapter = assignmentAdapter
                    assignmentAdapter.updateData(assignmentList)
                }
        }
    }
}