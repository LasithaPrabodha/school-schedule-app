package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.AssignmentListItemBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment

class AssignmentsAdapter(private var assignmentList: List<Assignment>, private val classViewModel: HomeViewModel, private val context: Context) : RecyclerView.Adapter<AssignmentsAdapter.ViewHolder>() {

    fun updateData(newAssignmentList: List<Assignment>) {
        assignmentList = newAssignmentList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AssignmentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val assignmentItem = assignmentList[position]
        holder.bind(assignmentItem)

        holder.binding.imageButton.setOnClickListener {
            val currentAssignment = Assignment(assignmentItem.id, assignmentItem.title, assignmentItem.detail, assignmentItem.isPriority, assignmentItem.isCompleted, assignmentItem.classId)
            AlertDialog.Builder(context).apply {
                setTitle("Delete assignment")
                setMessage("Are you sure you want to delete this assignment")
                setPositiveButton("Delete"){_,_ ->
                    deleteAssignment(currentAssignment)
                    Toast.makeText(context, "Assignment deleted", Toast.LENGTH_LONG).show()
                }
                setNegativeButton("Cancel", null)
            }.create().show()
        }

        holder.binding.assignmentCheckBox.setOnClickListener {
            val assignmentChecked = holder.binding.assignmentCheckBox.toString().toBoolean()
            if (assignmentChecked){
                val editedAssignment = Assignment(assignmentItem.id, assignmentItem.title, assignmentItem.detail, assignmentItem.isPriority, false, assignmentItem.classId)
                classViewModel.editAssignment(editedAssignment)
                Toast.makeText(context, "Assignment edited", Toast.LENGTH_LONG).show()
            } else {
                val editedAssignment = Assignment(assignmentItem.id, assignmentItem.title, assignmentItem.detail, assignmentItem.isPriority, true, assignmentItem.classId)
                classViewModel.editAssignment(editedAssignment)
                Toast.makeText(context, "Assignment edited", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int = assignmentList.size

    class ViewHolder(val binding: AssignmentListItemBinding) :
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
