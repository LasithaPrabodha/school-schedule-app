package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.AssignmentListItemBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.ClassWithAssignments

class AssignmentsAdapter(
    private val context: Context,
    private var assignmentList: MutableList<Assignment>,
    private val onEdit: (Assignment) -> Unit,
    private val onDelete: (Assignment) -> Unit,
) : RecyclerView.Adapter<AssignmentsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AssignmentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val assignmentItem = assignmentList[position]

        holder.binding.imageButton.setOnClickListener {
            val currentAssignment = assignmentItem.copy()

            AlertDialog.Builder(context).apply {
                setTitle("Delete assignment")
                setMessage("Are you sure you want to delete this assignment")
                setPositiveButton("Delete") { _, _ ->
                    onDelete(currentAssignment)
                    Toast.makeText(context, "Assignment deleted", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel", null)
            }.create().show()
        }

        holder.binding.assignmentCheckBox.setOnClickListener {
            val editedAssignment = assignmentItem.copy()
            editedAssignment.isCompleted = !editedAssignment.isCompleted

            onEdit(editedAssignment)
        }

        holder.bind(assignmentItem)
    }

    override fun getItemCount(): Int = assignmentList.size

    inner class ViewHolder(val binding: AssignmentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(assignment: Assignment) {
            binding.apply {
                assignmentCheckBox.text = assignment.title
                assignmentCheckBox.isChecked = assignment.isCompleted

                if (assignment.isCompleted) {
                    assignmentCheckBox.paintFlags =
                        assignmentCheckBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    assignmentDetails.paintFlags =
                        assignmentDetails.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    assignmentCheckBox.paintFlags =
                        assignmentCheckBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    assignmentDetails.paintFlags =
                        assignmentDetails.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }

                if (assignment.detail != "") {
                    assignmentDetails.text = assignment.detail
                    assignmentDetails.visibility = View.VISIBLE
                } else {
                    assignmentDetails.visibility = View.GONE
                }
            }
        }
    }

    fun updateList(items: List<Assignment>) {
        this.assignmentList.clear()
        this.assignmentList.addAll(items)
        notifyDataSetChanged()
    }

}