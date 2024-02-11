package com.capstoneprojectg8.schoolscheduleapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "classes")
@Parcelize
data class Class(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val classCode: String,
    val className:String,
    val colour: String
):Parcelable

