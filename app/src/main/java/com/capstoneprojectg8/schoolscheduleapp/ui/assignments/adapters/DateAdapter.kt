package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemDateOfClassBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.ClassWithAssignmentsByDate
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper

class DateAdapter(
    private val context: Context,
    private var items: MutableList<ClassWithAssignmentsByDate>,
    private val onEdit: (Assignment) -> Unit,
    private val onDelete: (Assignment) -> Unit,
) : RecyclerView.Adapter<DateAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemDateOfClassBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(classItem: ClassWithAssignmentsByDate) {
            binding.apply {
                tvDate.text = DateHelper.getSmartDate(classItem.date)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDateOfClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val classesAdapter =
            ClassesAdapter(context, item.classWithAssignments.toMutableList(), onEdit, onDelete)

        holder.binding.rvClassWithAssignments.adapter = classesAdapter

        holder.bind(item)
    }

    fun updateList(items: List<ClassWithAssignmentsByDate>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}