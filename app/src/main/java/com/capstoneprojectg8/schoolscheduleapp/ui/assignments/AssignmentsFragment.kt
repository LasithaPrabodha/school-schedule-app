package com.capstoneprojectg8.schoolscheduleapp.ui.assignments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentAssignmentsBinding
import com.capstoneprojectg8.schoolscheduleapp.models.ClassAssignments
import com.capstoneprojectg8.schoolscheduleapp.ui.home.AssignmentsAdapter
import com.capstoneprojectg8.schoolscheduleapp.ui.home.HomeViewModel

class AssignmentsFragment : Fragment(), ClassesAdapterDelegate {

    private var _binding: FragmentAssignmentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var classAdapter: ClassesAdapter
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAssignmentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupClassRecyclerView()
        return root
    }

    private fun setupClassRecyclerView() {
        viewModel.getAllAssignmentsWithClasses().observe(viewLifecycleOwner) { classes ->
            val filteredClasses = classes.filter { it.assignments.isNotEmpty() }

            if (filteredClasses.isEmpty()) {
                binding.emptyAssignmentsMsg.visibility = View.VISIBLE
            } else {
                binding.emptyAssignmentsMsg.visibility = View.GONE

            }

            classAdapter = ClassesAdapter(
                this,
                requireContext(),
                filteredClasses
            )
            binding.rvAssignments.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = classAdapter
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBindViewHolder(holder: ClassesAdapter.ViewHolder, item: ClassAssignments) {
        val assignmentAdapter =
            AssignmentsAdapter(item.assignments, viewModel, requireContext())
        holder.binding.rvAssignments.adapter = assignmentAdapter
        assignmentAdapter.updateData(item.assignments)
    }
}