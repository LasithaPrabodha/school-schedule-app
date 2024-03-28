package com.capstoneprojectg8.schoolscheduleapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classes")
data class ClassEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var code: String,
    var name: String,
    val colour: Int
)

