package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.NavArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemClassBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import com.capstoneprojectg8.schoolscheduleapp.models.ScheduleSlot
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ClassesAdapter(
    private val context: Context,
    private val onItemClicked: (ScheduleSlot) -> Unit,
    private var items: List<ScheduleSlot>,
    private val classViewModel: HomeViewModel
) : RecyclerView.Adapter<ClassesAdapter.ViewHolder>() {

    private lateinit var assignmentList: List<Assignment>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        viewHolder.binding.AssignmentRv.layoutManager = LinearLayoutManager(parent.context)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            isAnyItemExpanded(position)
            item.isExpandable = !item.isExpandable
            if (item.isExpandable) {
                holder.itemView.findViewTreeLifecycleOwner()?.let { it1 ->
                    classViewModel.getAssignmentListByClassId(item.id)
                        .observe(it1) { assignments ->
                            assignmentList = assignments ?: emptyList()
                            val assignmentAdapter = AssignmentsAdapter(assignmentList, classViewModel, context)
                            holder.binding.AssignmentRv.adapter = assignmentAdapter
                            assignmentAdapter.updateData(assignmentList)
                        }
                }
            }
            notifyItemChanged(position, Unit)
        }

        holder.bind(item)

        holder.binding.addAssignmentBtn.setOnClickListener {
            onItemClicked.invoke(item)
        }

        holder.binding.cancelBtn.setOnClickListener {
            item.isExpandable = false
            notifyItemChanged(position, Unit)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty() && payloads[0] == 0) {
            holder.collapseExpandedViews()
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ItemClassBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(classItem: ScheduleSlot) {
            binding.apply {
                val startTime = classItem.startingHour
                val endTime = classItem.startingHour + classItem.noOfHours
                val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())

                tvStartTime.text = sdf.format(Date().apply { hours = startTime; minutes = 0 })
                tvCourseName.setTextColor(ContextCompat.getColor(context, classItem.color))
                tvEndTime.text = sdf.format(Date().apply { hours = endTime; minutes = 0 })
                tvRoom.text = "Room: " + classItem.classRoom
                tvCourseName.text = classItem.className
                expandableAssignmentContainter.visibility =
                    if (classItem.isExpandable) View.VISIBLE else View.GONE
                dividerLine.setBackgroundColor(ContextCompat.getColor(context, classItem.color))

            }
        }

        fun collapseExpandedViews() {
            binding.expandableAssignmentContainter.visibility = View.GONE
        }
    }

    fun updateData(newItems: List<ScheduleSlot>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = items.indexOfFirst {
            it.isExpandable
        }

        if (temp >= 0 && temp != position) {
            items[temp].isExpandable = false
            notifyItemChanged(temp, 0)
        }
    }
}