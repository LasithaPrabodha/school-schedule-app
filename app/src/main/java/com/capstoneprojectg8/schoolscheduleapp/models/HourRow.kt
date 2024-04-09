package com.capstoneprojectg8.schoolscheduleapp.models

data class HourRow(
    val hour: String,
    val amPm: String,
    val week: List<Map<String, String>>
)
