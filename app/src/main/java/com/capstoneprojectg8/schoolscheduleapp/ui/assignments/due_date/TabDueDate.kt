package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.due_date

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentTabDueDateBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.ui.assignments.adapters.DateAdapter

class TabDueDate : Fragment() {
    private lateinit var dateAdapter: DateAdapter
    private val viewModel: TabDueDateViewModel by activityViewModels()

    private var _binding: FragmentTabDueDateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabDueDateBinding.inflate(inflater, container, false)
        dateAdapter = DateAdapter(
            requireContext(),
            mutableListOf(),
            { assignment: Assignment -> viewModel.editAssignment(assignment) },
            { assignment: Assignment -> viewModel.deleteAssignment(assignment) }
        )

        binding.rvClassByDate.adapter = dateAdapter

        setupClassRecyclerView()
        return binding.root
    }

    private fun setupClassRecyclerView() {
        viewModel.filterAssignmentsByDueDate().observe(viewLifecycleOwner) { classes ->
            if (classes.isEmpty()) {
                binding.emptyAssignmentsMsg.visibility = View.VISIBLE
            } else {
                binding.emptyAssignmentsMsg.visibility = View.GONE

            }

            // have to call update when assignment deleted
            dateAdapter.updateList(classes)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}