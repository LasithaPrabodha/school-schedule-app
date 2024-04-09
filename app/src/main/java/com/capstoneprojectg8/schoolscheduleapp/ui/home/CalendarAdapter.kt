package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemDayOfWeekBinding
import com.capstoneprojectg8.schoolscheduleapp.models.CalendarData
import com.capstoneprojectg8.schoolscheduleapp.utils.ThemeHelper.isDarkModeEnabled


interface CalendarAdapterDelegate {
    fun onSelect(calendarData: CalendarData, position: Int, day: TextView)
}

class CalendarAdapter(
    private val calendarAdapterDelegate: CalendarAdapterDelegate,
    private val datesList: ArrayList<CalendarData>
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    var pos: Int = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.ViewHolder {
        val binding =
            ItemDayOfWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarAdapter.ViewHolder, position: Int) {
        holder.bind(datesList[position], position)
    }

    override fun getItemCount(): Int = datesList.size

    fun updateData(calendarList: ArrayList<CalendarData>) {
        datesList.clear()
        datesList.addAll(calendarList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemDayOfWeekBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(calendarDataModel: CalendarData, position: Int) {
            val date = binding.dateTextView
            val day = binding.weekdayTextView
            val cell = binding.root
            val isDarkMode = isDarkModeEnabled(binding.root.context)
            val backgroundColor: Int
            val textColor: Int

            if (pos == position) {
                calendarDataModel.isSelected = true
            }


            if (calendarDataModel.isSelected) {
                pos = -1

                if (isDarkMode) {
                    backgroundColor = ContextCompat.getColor(binding.root.context, R.color.black)
                    textColor = ContextCompat.getColor(binding.root.context, R.color.white)
                } else {
                    backgroundColor = ContextCompat.getColor(binding.root.context, R.color.color_action)
                    textColor = ContextCompat.getColor(binding.root.context, R.color.black)
                }

                val shape = GradientDrawable()
                shape.shape = GradientDrawable.RECTANGLE
                shape.setColor(backgroundColor)
                shape.cornerRadii = floatArrayOf(16f, 16f, 16f, 16f, 16f, 16f, 16f, 16f)
                ViewCompat.setBackground(cell, shape)
                date.setTextColor(textColor)
                day.setTextColor(textColor)
            } else {
                backgroundColor =
                    ContextCompat.getColor(binding.root.context, R.color.background)


                val shape = GradientDrawable()
                shape.shape = GradientDrawable.RECTANGLE
                ViewCompat.setBackground(cell, shape)
            }

            date.text = calendarDataModel.calendarDate
            day.text = calendarDataModel.calendarDay
            cell.setOnClickListener {
                calendarAdapterDelegate.onSelect(calendarDataModel, adapterPosition, date)
            }

        }
    }

    fun setPosition(pos: Int) {
        this.pos = pos
    }

}