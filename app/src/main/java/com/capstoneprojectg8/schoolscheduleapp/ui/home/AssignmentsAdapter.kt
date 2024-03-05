package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.AssignmentListItemBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment

class AssignmentsAdapter(private var assignmentList: List<Assignment>, private val classViewModel: HomeViewModel) : RecyclerView.Adapter<AssignmentsAdapter.ViewHolder>() {

    fun updateData(newAssignmentList: List<Assignment>) {
        assignmentList = newAssignmentList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AssignmentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.imageButton.setOnClickListener {
            //deleteAssignment()
        }

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val assignmentItem = assignmentList[position]
        holder.bind(assignmentItem)
    }

    override fun getItemCount(): Int = assignmentList.size

    class ViewHolder(private val binding: AssignmentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(assignment: Assignment) {
            binding.apply {
                assignmentCheckBox.text = assignment.title
            }
        }
    }

    private fun deleteAssignment(assignment: Assignment) {
        classViewModel.deleteAssignment(assignment)
    }
}
