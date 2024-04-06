package com.capstoneprojectg8.schoolscheduleapp.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout.LayoutParams
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemWeekDayCellBinding
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper

class CalendarCellAdapter(
    private val cellWidth: Int,
    private val isCurrentWeek: Boolean,
    private val today: String,
    private val setBackgroundColor: (view: View) -> Unit,
    private val onClick: (day: String) -> Unit
) :
    RecyclerView.Adapter<CalendarCellAdapter.CellViewHolder>() {

    private val dataSet = DateHelper.getDaysOfWeek()

    inner class CellViewHolder(val binding: ItemWeekDayCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: String) {
            binding.day = day

            binding.root.layoutParams = LinearLayout.LayoutParams(
                cellWidth,
                LayoutParams.MATCH_PARENT
            )

            if (isCurrentWeek && day == today) {
                setBackgroundColor(binding.root)
            }

            binding.weekCell.setOnClickListener {
                onClick(day)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarCellAdapter.CellViewHolder {
        val binding =
            ItemWeekDayCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CellViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.count()

    override fun onBindViewHolder(holder: CalendarCellAdapter.CellViewHolder, position: Int) {
        val item = dataSet[position]

        holder.bind(item)
    }

}