package com.capstoneprojectg8.schoolscheduleapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SClass(
    val id: Int,
    var code: String,
    var name: String,
    val colour: Int,
    var isExpandable: Boolean = false
) : Parcelable

