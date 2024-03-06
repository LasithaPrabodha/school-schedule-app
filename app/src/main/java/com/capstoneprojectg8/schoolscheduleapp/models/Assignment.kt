package com.capstoneprojectg8.schoolscheduleapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "assignment")
@Parcelize
data class Assignment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val detail: String,
    val isPriority: Boolean = false,
    val isCompleted: Boolean,
    val classId: Int
):Parcelable

