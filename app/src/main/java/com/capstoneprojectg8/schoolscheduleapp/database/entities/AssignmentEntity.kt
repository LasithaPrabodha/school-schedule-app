package com.capstoneprojectg8.schoolscheduleapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignment")
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val detail: String,
    val isPriority: Boolean = false,
    val isCompleted: Boolean,
    val classId: Int
)

