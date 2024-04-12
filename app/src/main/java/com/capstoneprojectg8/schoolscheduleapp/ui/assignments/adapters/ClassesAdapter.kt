package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemClassAssignmentsBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.ClassWithAssignments

class ClassesAdapter(
    private val context: Context,
    private var items: MutableList<ClassWithAssignments>,
    private val onEdit: (Assignment) -> Unit,
    private val onDelete: (Assignment) -> Unit,
) : RecyclerView.Adapter<ClassesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemClassAssignmentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        val assignmentAdapter =
            AssignmentsAdapter(context, mutableListOf(), onEdit, onDelete)

        assignmentAdapter.updateList(item.assignments.toMutableList())
        holder.binding.rvAssignments.adapter = assignmentAdapter

        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ItemClassAssignmentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(classItem: ClassWithAssignments) {
            binding.apply {
                tvCourseName.setTextColor(ContextCompat.getColor(context, classItem.sclass.colour))
                tvCourseName.text = classItem.sclass.name
            }
        }

    }

    fun updateList(_items: List<ClassWithAssignments>) {
        this.items.clear()
        this.items.addAll(_items)
        notifyDataSetChanged()
    }
}