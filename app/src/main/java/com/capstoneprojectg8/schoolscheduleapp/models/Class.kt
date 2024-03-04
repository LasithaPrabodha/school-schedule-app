package com.capstoneprojectg8.schoolscheduleapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "classes")
@Parcelize
data class Class(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var classCode: String,
    var className:String,
    val colour: Int,
    val room: String,
    val startTime: String,
    val endTime: String,
    var isExpandable: Boolean = false,
):Parcelable

