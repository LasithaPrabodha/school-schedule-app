package com.capstoneprojectg8.schoolscheduleapp.ui.assignments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentAssignmentTabsBinding
import com.capstoneprojectg8.schoolscheduleapp.ui.assignments.classes.TabClasses
import com.capstoneprojectg8.schoolscheduleapp.ui.assignments.due_date.TabDueDate
import com.capstoneprojectg8.schoolscheduleapp.ui.assignments.priority.TabPriority
import com.google.android.material.tabs.TabLayout

class AssignmentsFragment : Fragment() {

    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout  // creating object of TabLayout

    private var _binding: FragmentAssignmentTabsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssignmentTabsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        pager = binding.viewPager
        tab = binding.tabLayout


        // Initializing the ViewPagerAdapter
        val adapter = TabFragmentAdapter(requireActivity().supportFragmentManager)

        // add fragment to the list
        adapter.addFragment(TabDueDate(), "Due Date")
        adapter.addFragment(TabClasses(), "Classes")
        adapter.addFragment(TabPriority(), "Priority")

        // Adding the Adapter to the ViewPager
        pager.adapter = adapter

        // bind the viewPager with the TabLayout.
        tab.setupWithViewPager(pager)

        return root
    }

}