package com.capstoneprojectg8.schoolscheduleapp.models

data class ClassWithAssignments(
    val sclass: SClass,
    val assignments: List<Assignment>
)