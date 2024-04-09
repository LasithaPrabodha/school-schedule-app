package com.capstoneprojectg8.schoolscheduleapp.models

data class ClassWithAssignmentsByDate(
    val date: String,
    val classWithAssignments: List<ClassWithAssignments>
)
