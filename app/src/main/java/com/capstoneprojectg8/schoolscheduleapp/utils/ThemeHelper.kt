package com.capstoneprojectg8.schoolscheduleapp.utils

import android.content.Context
import android.content.res.Configuration

object ThemeHelper {
    fun isDarkModeEnabled(context: Context): Boolean {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }
}