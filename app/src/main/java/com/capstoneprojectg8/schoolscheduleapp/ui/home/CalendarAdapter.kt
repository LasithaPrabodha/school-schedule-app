package com.capstoneprojectg8.schoolscheduleapp.ui.home

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

class CalendarAdapter(
    private val calendarInterface: CalendarInterface,
    private val datesList: ArrayList<CalendarData>
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    var pos: Int = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.ViewHolder {
        val binding = ItemDayOfWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            fun bind(calendarDataModel: CalendarData, position: Int){
                val date = binding.dateTextView
                val day = binding.weekdayTextView
                val linearLayout = binding.root

                if (pos == position) {
                    calendarDataModel.isSelected = true
                }
                if (calendarDataModel.isSelected) {
                    pos = -1
                    val shape = GradientDrawable()
                    shape.shape = GradientDrawable.RECTANGLE
                    shape.setColor(ContextCompat.getColor(binding.root.context, R.color.background))
                    shape.cornerRadii = floatArrayOf(16f, 16f, 16f, 16f, 0f, 0f, 0f, 0f)
                    ViewCompat.setBackground(linearLayout, shape)
                } else {
                    val shape = GradientDrawable()
                    shape.shape = GradientDrawable.RECTANGLE
                    shape.setColor(ContextCompat.getColor(binding.root.context, R.color.white))
                    ViewCompat.setBackground(linearLayout, shape)
                }

                date.text = calendarDataModel.calendarDate
                day.text = calendarDataModel.calendarDay
                linearLayout.setOnClickListener {
                    calendarInterface.onSelect(calendarDataModel, adapterPosition, date)
                }

            }


    }

    interface CalendarInterface {
        fun onSelect(calendarData: CalendarData, position: Int, day: TextView)
    }

    fun setPosition(pos: Int){
        this.pos = pos
    }
}