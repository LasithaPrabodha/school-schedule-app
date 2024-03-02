package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemClassBinding
import com.capstoneprojectg8.schoolscheduleapp.models.ClassItem


class ClassesAdapter(private val context: Context, private var items: List<ClassItem>) : RecyclerView.Adapter<ClassesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            isAnyItemExpanded(position)
            item.isExpandable = !item.isExpandable
            notifyItemChanged(position, Unit)
        }
        holder.bind(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isNotEmpty() && payloads[0] == 0){
            holder.collapseExpandedViews()
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemClassBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(classItem: ClassItem) {
            binding.apply {
                tvStartTime.text = classItem.startTime
                tvCourseName.setTextColor(ContextCompat.getColor(context,classItem.color))
                tvEndTime.text = classItem.endTime
                tvRoom.text = classItem.room
                tvCourseName.text = classItem.courseName
                expandableAssignmentContainter.visibility = if (classItem.isExpandable) View.VISIBLE else View.GONE

            }
        }

        fun collapseExpandedViews(){
            binding.expandableAssignmentContainter.visibility = View.GONE
        }
    }

    fun updateData(newItems: List<ClassItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = items.indexOfFirst {
            it.isExpandable
        }

        if(temp >= 0 && temp != position) {
            items[temp].isExpandable = false
            notifyItemChanged(temp,0)
        }
    }

}