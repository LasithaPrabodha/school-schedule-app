package com.capstoneprojectg8.schoolscheduleapp.ui.schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemCalendarRowBinding
import com.capstoneprojectg8.schoolscheduleapp.models.HourRow
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper
import java.util.Locale

class CalendarRowAdapter(
    private val context: Context,
    private val dataSet: MutableList<HourRow>,
    private val isCurrentWeek: Boolean,
    private val cellWidth: Int,
    private val onCellClick: (String, HourRow) -> Unit
) :
    RecyclerView.Adapter<CalendarRowAdapter.TimeViewHolder>() {

    private val today: String = DateHelper.getToday("EEEE").lowercase(Locale.ROOT)

    inner class TimeViewHolder(val binding: ItemCalendarRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(row: HourRow) {
            binding.hour.text = row.hour
            binding.amPm.text = row.amPm
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding =
            ItemCalendarRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TimeViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: TimeViewHolder, position: Int) {
        val item = dataSet[position]

        val setBackground: (View) -> Unit = { cell ->
            cell.setBackgroundColor(ContextCompat.getColor(context, R.color.background))
        }

        val calendarCellAdapter =
            CalendarCellAdapter(cellWidth, isCurrentWeek, today, setBackground) { day ->
                onCellClick(day, item)
            }

        viewHolder.binding.weekdays.apply {
            adapter = calendarCellAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
        }

        viewHolder.bind(item)
    }

    fun updateList(list: List<HourRow>) {
        dataSet.clear()
        dataSet.addAll(list)
        notifyItemRangeChanged(0, 24)
    }

    override fun getItemCount() = dataSet.size
}