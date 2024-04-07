package com.capstoneprojectg8.schoolscheduleapp.ui.schedule

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemWeekDayBinding
import com.capstoneprojectg8.schoolscheduleapp.utils.DateHelper


class WeekTimelineAdapter(
    private val context: Context,
    private var dayList: MutableList<Map<String, String>>,
    private val cellWidth: Int
) :
    RecyclerView.Adapter<WeekTimelineAdapter.DayViewHolder>() {

    private val today: String = DateHelper.getToday("yyyy-MM-dd")

    inner class DayViewHolder(val binding: ItemWeekDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Map<String, String>) {
            binding.date.text = item["date"]
            binding.day.text = item["weekday"]


            if (today == item["fullDate"]) {
                val shape = GradientDrawable()
                val isDarkMode = isDarkModeEnabled(binding.root.context)
                val textColor: Int

                shape.shape = GradientDrawable.RECTANGLE
                shape.cornerRadii = floatArrayOf(16f, 16f, 16f, 16f, 0f, 0f, 0f, 0f)

                if (isDarkMode){
                    textColor = ContextCompat.getColor(binding.root.context, R.color.white)
                    shape.setColor(ContextCompat.getColor(context, R.color.black))
                } else {
                    textColor = ContextCompat.getColor(binding.root.context, R.color.black)
                    shape.setColor(ContextCompat.getColor(context, R.color.background))
                }

                binding.apply {
                    date.setTextColor(textColor)
                    day.setTextColor(textColor)
                }

                binding.root.background = shape
            } else {
                binding.root.background = null
            }

            binding.root.layoutParams = LinearLayout.LayoutParams(
                cellWidth,
                context.resources.getDimensionPixelSize(R.dimen.grid_cell_layout_height)
            )

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeekTimelineAdapter.DayViewHolder {
        val binding =
            ItemWeekDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DayViewHolder(binding)
    }

    fun updateList(newList: MutableList<Map<String, String>>) {
        dayList.clear()
        dayList.addAll(newList)
        notifyItemRangeChanged(0, 5)
    }


    override fun onBindViewHolder(holder: WeekTimelineAdapter.DayViewHolder, position: Int) {
        val item = dayList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    private fun isDarkModeEnabled(context: Context): Boolean {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }
}
