package com.capstoneprojectg8.schoolscheduleapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClassSlot(
    val id: Int,
    val startingHour: Int,
    val dayOfTheWeek: Int,
    val noOfHours: Int,
    val classId: Int,
    val className: String,
    val classRoom: String,
    val color: Int,
    val date: String,
    var isExpandable: Boolean = false
) : Parcelable
