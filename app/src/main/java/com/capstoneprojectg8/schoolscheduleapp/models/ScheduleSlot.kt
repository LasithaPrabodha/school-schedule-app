package com.capstoneprojectg8.schoolscheduleapp.models

data class ScheduleSlot(
    val row: Int,
    val col: Int,
    val rowSpan: Int,
    val className: String,
    val classRoom: String,
    val color: Int
)
