package com.capstoneprojectg8.schoolscheduleapp.utils

import java.util.Locale

fun String.toTitleCase(delimiter: String = " "): String {
    return split(delimiter).joinToString(delimiter) { word ->
        word.lowercase(Locale.ROOT).replaceFirstChar(Char::titlecaseChar)
    }
}