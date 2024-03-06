package com.capstoneprojectg8.schoolscheduleapp.ui.schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemCalendarRowBinding
import com.capstoneprojectg8.schoolscheduleapp.models.HourRow
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper
import java.util.Locale

class CalendarRowAdapter(
    private val context: Context,
    private val dataSet: MutableList<HourRow>
) :
    RecyclerView.Adapter<CalendarRowAdapter.TimeViewHolder>() {

    private val today: String = DateHelper.getToday("EEEE").lowercase(Locale.ROOT)

    inner class TimeViewHolder(val binding: ItemCalendarRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cell: HourRow) {
            val view = mapOf(
                "monday" to binding.monday,
                "tuesday" to binding.tuesday,
                "wednesday" to binding.wednesday,
                "thursday" to binding.thursday,
                "friday" to binding.friday,
            )

            binding.hour.text = cell.hour
            binding.amPm.text = cell.amPm

            if (cell.isCurrentWeek) {
                view[today]?.setBackgroundColor(ContextCompat.getColor(context, R.color.background))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding =
            ItemCalendarRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TimeViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: TimeViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.bind(item)
    }

    fun updateList(list: List<HourRow>) {
        dataSet.clear()
        dataSet.addAll(list)
        notifyItemRangeChanged(0, 24)
    }

    override fun getItemCount() = dataSet.size
}