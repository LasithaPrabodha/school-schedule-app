package com.capstoneprojectg8.schoolscheduleapp.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

object DateHandler {
    fun getWeekDates(): MutableList<Map<String, String>> {
        val today = LocalDate.now()
        val currentWeekStart = if (today.dayOfWeek == DayOfWeek.SATURDAY || today.dayOfWeek == DayOfWeek.SUNDAY) {
            today.with(TemporalAdjusters.next(DayOfWeek.MONDAY)) // Start of next week
        } else {
            today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) // Start of current week
        }

        val weekData = mutableListOf<Map<String, String>>()

        for (i in 0 until 5) {
            val date = currentWeekStart.plusDays(i.toLong())
            val formattedDate = date.format(DateTimeFormatter.ofPattern("dd"))
            val weekday = date.dayOfWeek.name.toTitleCase()

            val entry = mapOf("date" to formattedDate, "weekday" to weekday.slice(IntRange(0, 2)))
            weekData.add(entry)
        }

        return weekData
    }

    fun getToday(pattern: String = "yyyy-MM-dd"): String {
        val today = LocalDate.now()
        return today.format(DateTimeFormatter.ofPattern(pattern))
    }
}