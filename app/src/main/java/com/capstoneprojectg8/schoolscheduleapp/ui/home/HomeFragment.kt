package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
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
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper.isSameDate
import com.capstoneprojectg8.schoolscheduleapp.utils.ThemeHelper.isDarkModeEnabled
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date


class HomeFragment : Fragment(), CalendarAdapterDelegate, ClassesAdapterDelegate {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var classAdapter: ClassSlotsAdapter
    private val classViewModel: HomeViewModel by activityViewModels()
    private lateinit var calendarAdapter: CalendarAdapter

    private val calendarList = ArrayList<CalendarData>()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.calendar_nav, menu)

                val previousItem = menu.findItem(R.id.previous)
                val nextItem = menu.findItem(R.id.next)

                previousItem.isVisible = false
                nextItem.isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {

                    R.id.current_week -> {
                        scrollToDate(Date())
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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

            val filtered = slots.filter {
                val todayDate = LocalDate.now()
                (it.date == today) || (it.dayOfTheWeek == todayDate.dayOfWeek.value && it.isRepeating)
            }

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
        val mStartD = Date()
        val dateList = mutableListOf<CalendarData>()
        val dates = mutableListOf<Date>()
        val calendar = Calendar.getInstance().apply {
            time = mStartD
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val maxDaysToShow = 100
        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)
        val prevMonthDays = firstDayOfMonth - 1
        val totalDays = maxDaysToShow - prevMonthDays

        calendar.add(Calendar.DATE, -prevMonthDays)

        for (i in 0 until totalDays) {
            val currentDate = calendar.time
            dates.add(currentDate)
            dateList.add(CalendarData(currentDate))
            calendar.add(Calendar.DATE, 1)
        }

        calendarList.apply {
            clear()
            addAll(dateList)
        }

        calendarAdapter.updateData(dateList)

        val scrollToPosition = calendarList.indexOfFirst { isSameDate(it.data, mStartD) }
        if (scrollToPosition != -1) {
            calendarAdapter.setPosition(scrollToPosition)
            binding.calendarView.scrollToPosition(scrollToPosition - 2)
        }

    }

    private fun scrollToDate(date: Date) {
        val scrollToPosition = calendarList.indexOfFirst { isSameDate(it.data, date) }
        if (scrollToPosition != -1) {
            onSelect(calendarList[scrollToPosition], scrollToPosition)

            val recyclerView = binding.calendarView
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstPosition = layoutManager.findFirstVisibleItemPosition()
            val lastPosition = layoutManager.findLastVisibleItemPosition()
            val visibleItems = lastPosition - firstPosition

            if (firstPosition < scrollToPosition) {
                recyclerView.smoothScrollToPosition(scrollToPosition + (visibleItems / 2))
            } else {
                recyclerView.smoothScrollToPosition(scrollToPosition - (visibleItems / 2))
            }
        }
    }

    private fun onAddAssignmentClick(slot: ClassSlot) {
        val action = HomeFragmentDirections.actionNavigationHomeToAddNewAssignmentFragment(
            slot.id
        )
        findNavController().navigate(action)
    }


    override fun onSelect(calendarData: CalendarData, position: Int) {
        calendarList.forEachIndexed { index, calendarData ->
            calendarData.isSelected = index == position
        }

        calendarAdapter.updateData(calendarList)

        val selectedDate = calendarData.data
        val formattedDate = DateHelper.formatDate(selectedDate)
        val selectedLocalDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        val filtered = classSlots.filter {
            (it.date == formattedDate) || (it.dayOfTheWeek == selectedLocalDate.dayOfWeek.value && it.isRepeating)
        }

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