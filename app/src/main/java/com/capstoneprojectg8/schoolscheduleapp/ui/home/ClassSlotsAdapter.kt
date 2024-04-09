package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemClassHomeBinding
import com.capstoneprojectg8.schoolscheduleapp.models.ClassSlot
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface ClassesAdapterDelegate {
    fun onExpandable(holder: ClassSlotsAdapter.ViewHolder, item: ClassSlot)
}

class ClassSlotsAdapter(
    private val classSlotDelegate: ClassesAdapterDelegate,
    private val context: Context,
    private val onItemClicked: (ClassSlot) -> Unit,
    private var items: List<ClassSlot>,
) : RecyclerView.Adapter<ClassSlotsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemClassHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            isAnyItemExpanded(position)
            item.isExpandable = !item.isExpandable
            if (item.isExpandable) {
                classSlotDelegate.onExpandable(holder, item)
            }
            notifyItemChanged(position, Unit)
        }

        holder.binding.addAssignmentBtn.setOnClickListener {
            onItemClicked.invoke(item)
        }

        holder.binding.cancelBtn.setOnClickListener {
            item.isExpandable = false
            notifyItemChanged(position, Unit)
        }

        holder.bind(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty() && payloads[0] == 0) {
            holder.collapseExpandedViews()
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ItemClassHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(classItem: ClassSlot) {
            binding.apply {
                val startTime = classItem.startingHour
                val endTime = classItem.startingHour + classItem.noOfHours
                val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())

                tvStartTime.text = sdf.format(Date().apply { hours = startTime; minutes = 0 })
                tvCourseName.setTextColor(ContextCompat.getColor(context, classItem.color))
                tvEndTime.text = sdf.format(Date().apply { hours = endTime; minutes = 0 })
                tvRoom.text = "Room: " + classItem.classRoom
                tvCourseName.text = classItem.className
                expandableAssignmentContainer.visibility =
                    if (classItem.isExpandable) View.VISIBLE else View.GONE
                dividerLine.setBackgroundColor(ContextCompat.getColor(context, classItem.color))
                ivArrow.setImageResource(
                    if (classItem.isExpandable) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_right_24
                )

            }
        }

        fun collapseExpandedViews() {
            binding.expandableAssignmentContainer.visibility = View.GONE
            binding.ivArrow.setImageResource(R.drawable.baseline_keyboard_arrow_right_24)
        }
    }

    fun updateData(newItems: List<ClassSlot>) {
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