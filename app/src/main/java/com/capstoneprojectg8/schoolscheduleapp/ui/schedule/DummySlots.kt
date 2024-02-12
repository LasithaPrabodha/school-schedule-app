package com.capstoneprojectg8.schoolscheduleapp.ui.schedule

import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.models.ScheduleSlot

object DummySlots {
    fun getSlots() = mutableListOf<ScheduleSlot>().apply {
        add(ScheduleSlot(9, 1, 1, "Android", "Room 1",R.color.orange))
        add(ScheduleSlot(10, 1, 1, "iOS", "Room 1", R.color.green))
        add(ScheduleSlot(11, 1, 1, "Mobile Security", "Room 2", R.color.purple))
        add(ScheduleSlot(12, 1, 3, "Capstone Project", "Room 3", R.color.blue))
        add(ScheduleSlot(9, 2, 2, "Capstone Project", "Room 1", R.color.blue))
        add(ScheduleSlot(11, 2, 2, "Android", "Room 1", R.color.orange))
        add(ScheduleSlot(10, 3, 1, "iOS", "Room 2", R.color.green))
        add(ScheduleSlot(11, 3, 2, "Mobile Security", "Room 3",R.color.purple))
        add(ScheduleSlot(9, 4, 2, "Android", "Room 1", R.color.orange))
        add(ScheduleSlot(11, 4, 1, "Moblie Security", "Room 2", R.color.purple))
        add(ScheduleSlot(9, 5, 1, "Moblie Security","Room 3", R.color.purple))
        add(ScheduleSlot(10, 5, 1, "Android","Room 1", R.color.orange))
        add(ScheduleSlot(11, 5, 4, "iOS","Room 1", R.color.green))
    }
}