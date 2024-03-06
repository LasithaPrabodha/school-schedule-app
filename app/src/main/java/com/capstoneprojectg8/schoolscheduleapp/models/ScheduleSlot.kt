package com.capstoneprojectg8.schoolscheduleapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "class_slots")
@Parcelize
data class ScheduleSlot(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val startingHour: Int,
    val dayOfTheWeek: Int,
    val noOfHours: Int,
    val className: String,
    val classRoom: String,
    val color: Int,
    val date: String,
    var isExpandable: Boolean = false
) : Parcelable
