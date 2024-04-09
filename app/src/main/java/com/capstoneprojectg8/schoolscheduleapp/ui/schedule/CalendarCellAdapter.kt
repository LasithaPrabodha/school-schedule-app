package com.capstoneprojectg8.schoolscheduleapp.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout.LayoutParams
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemWeekDayCellBinding
import com.capstoneprojectg8.schoolscheduleapp.models.HourRow
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper
import com.capstoneprojectg8.schoolscheduleapp.utils.ThemeHelper.isDarkModeEnabled

class CalendarCellAdapter(
    private val cellWidth: Int,
    private val week: List<Map<String, String>>,
    private val today: String,
    private val onClick: (day: String) -> Unit
) :
    RecyclerView.Adapter<CalendarCellAdapter.CellViewHolder>() {

    inner class CellViewHolder(val binding: ItemWeekDayCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Map<String, String>) {
            binding.day = day["weekdayFull"]?.lowercase()

            binding.root.layoutParams = LinearLayout.LayoutParams(
                cellWidth,
                LayoutParams.MATCH_PARENT
            )
            val isDarkMode = isDarkModeEnabled(binding.root.context)


            if (day["fullDate"] == today) {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        if (isDarkMode) R.color.black else R.color.background
                    )
                )
            }

            binding.weekCell.setOnClickListener {
                onClick(day["weekdayFull"]!!.lowercase())
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

    override fun getItemCount(): Int = week.count()

    override fun onBindViewHolder(holder: CalendarCellAdapter.CellViewHolder, position: Int) {
        val item = week[position]

        holder.bind(item)
    }

}