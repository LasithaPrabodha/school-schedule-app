package com.capstoneprojectg8.schoolscheduleapp.models

data class ScheduleSlot(
    val startingHour: Int,
    val dayOfTheWeek: Int,
    val noOfHours: Int,
    val className: String,
    val classRoom: String,
    val color: Int,
    val date: String? = null,
)
