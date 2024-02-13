package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemClassBinding
import com.capstoneprojectg8.schoolscheduleapp.models.ClassItem


class ClassesAdapter(private val context: Context,private var items: List<ClassItem>) : RecyclerView.Adapter<ClassesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }


    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemClassBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(classItem: ClassItem) {
            binding.tvStartTime.text = classItem.startTime
            binding.tvCourseName.setTextColor(ContextCompat.getColor(context,classItem.color))
            binding.tvEndTime.text = classItem.endTime
            binding.tvRoom.text = classItem.room
            binding.tvCourseName.text = classItem.courseName
        }
    }

    fun updateData(newItems: List<ClassItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}