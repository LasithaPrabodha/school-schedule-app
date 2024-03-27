package com.capstoneprojectg8.schoolscheduleapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classes")
data class ClassEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var classCode: String,
    var className: String,
    val colour: Int,
    var isExpandable: Boolean = false,
)

