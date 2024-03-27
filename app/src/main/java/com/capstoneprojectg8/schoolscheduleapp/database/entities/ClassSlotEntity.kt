package com.capstoneprojectg8.schoolscheduleapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "class_slots")
data class ClassSlotEntity(
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
)
