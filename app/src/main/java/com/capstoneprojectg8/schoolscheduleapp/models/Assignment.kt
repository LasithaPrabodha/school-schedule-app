package com.capstoneprojectg8.schoolscheduleapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Assignment(
    val id: Int,
    val title: String,
    val detail: String,
    val isPriority: Boolean = false,
    val isCompleted: Boolean,
    val classId: Int,
    val classSlotId: Int
):Parcelable

