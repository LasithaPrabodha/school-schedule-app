package com.capstoneprojectg8.schoolscheduleapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Assignment(
    val id: Int,
    val title: String,
    val detail: String,
    val dueDate: String,
    val isPriority: Boolean = false,
    var isCompleted: Boolean,
    val classId: Int,
    val classSlotId: Int
) : Parcelable

