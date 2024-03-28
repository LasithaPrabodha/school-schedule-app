package com.capstoneprojectg8.schoolscheduleapp.ui.assignments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemClassAssignmentsBinding
import com.capstoneprojectg8.schoolscheduleapp.models.ClassAssignments


class ClassesAdapter(
    private val classDelegate: ClassesAdapterDelegate,
    private val context: Context,
    private var items: List<ClassAssignments>,
) : RecyclerView.Adapter<ClassesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemClassAssignmentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        viewHolder.binding.rvAssignments.layoutManager = LinearLayoutManager(parent.context)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        classDelegate.onBindViewHolder(holder, item)

        holder.bind(item)
    }

    interface ClassesAdapterDelegate {
        fun onBindViewHolder(holder: ViewHolder, item: ClassAssignments)
    }
    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ItemClassAssignmentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(classItem: ClassAssignments) {
            binding.apply {
                tvCourseName.setTextColor(ContextCompat.getColor(context, classItem.sclass.colour))
                tvCourseName.text = classItem.sclass.name
                expandableAssignmentContainter.visibility = View.VISIBLE
            }
        }

    }

    fun updateData(newItems: List<ClassAssignments>) {
        items = newItems
        notifyDataSetChanged()
    }
}