package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentTabClassesBinding
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentTabDueDateBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.ui.assignments.adapters.ClassesAdapter
import com.capstoneprojectg8.schoolscheduleapp.ui.assignments.due_date.TabDueDateViewModel
import com.capstoneprojectg8.schoolscheduleapp.ui.home.AssignmentsAdapter
import com.capstoneprojectg8.schoolscheduleapp.ui.home.HomeViewModel


class TabClasses : Fragment() {

    private val viewModel: TabClassesViewModel by activityViewModels()

    private var _binding: FragmentTabClassesBinding? = null
    private val binding get() = _binding!!
    private lateinit var classAdapter: ClassesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabClassesBinding.inflate(inflater, container, false)

        classAdapter = ClassesAdapter(
            requireContext(),
            mutableListOf(),
            { assignment: Assignment -> viewModel.editAssignment(assignment) },
            { assignment: Assignment -> viewModel.deleteAssignment(assignment) }

        )

        binding.rvAssignments.adapter = classAdapter

        setupClassRecyclerView()
        return binding.root
    }


    private fun setupClassRecyclerView() {
        viewModel.getAllAssignmentsWithClasses().observe(viewLifecycleOwner) { classes ->

            if (classes.isEmpty()) {
                binding.emptyAssignmentsMsg.visibility = View.VISIBLE
            } else {
                binding.emptyAssignmentsMsg.visibility = View.GONE

            }

            // have to call update when assignment deleted
            classAdapter.updateList(classes)

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}