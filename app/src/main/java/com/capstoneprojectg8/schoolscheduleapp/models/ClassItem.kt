package com.capstoneprojectg8.schoolscheduleapp.models

data class ClassItem(
    val startTime: String,
    val endTime: String,
    val courseCode: String,
    val courseName: String,
    val room: String,
    val assignments: List<Assignments>,
    var isExpandable: Boolean = false,
    val color: Int
)

data class Assignments(
    val title: String,
    val isChecked: Boolean,
    val note: String?
)
