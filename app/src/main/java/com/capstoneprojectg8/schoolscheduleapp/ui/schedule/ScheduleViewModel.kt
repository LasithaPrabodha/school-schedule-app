package com.capstoneprojectg8.schoolscheduleapp.ui.schedule

import androidx.lifecycle.ViewModel
import com.capstoneprojectg8.schoolscheduleapp.models.HourRow

class ScheduleViewModel : ViewModel() {

    fun generateHourRows(isCurrentWeek: Boolean): MutableList<HourRow> {
        val list = mutableListOf<HourRow>()
        for (index in 0..24) {
            val hour = if (index == 0) {
                "12"
            } else if (index < 10) {
                "0${index}"
            } else if (index > 12) {
                if (index - 12 < 10) "0${(index - 12)}"
                else (index - 12).toString()
            } else {
                index.toString()
            }

            val amPm = if (index < 12) "AM" else "PM"

            list.add(
                HourRow(
                    amPm = amPm,
                    hour = hour,
                    isCurrentWeek = isCurrentWeek
                )
            )
        }
        return list
    }
}