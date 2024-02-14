package com.capstoneprojectg8.schoolscheduleapp.models

data class ClassItem(
    val startTime: String,
    val endTime: String,
    val courseCode: String,
    val courseName: String,
    val room: String,
    val assignments: List<Assignment>,
    val isCancelable: Boolean,
    val color: Int
)

data class Assignment(
    val title: String,
    val isChecked: Boolean,
    val note: String?
)
