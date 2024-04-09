package com.capstoneprojectg8.schoolscheduleapp.utils

import com.capstoneprojectg8.schoolscheduleapp.databinding.FragmentHomeBinding
import com.capstoneprojectg8.schoolscheduleapp.models.CalendarData
import com.capstoneprojectg8.schoolscheduleapp.models.ClassSlot
import com.capstoneprojectg8.schoolscheduleapp.ui.home.CalendarAdapter
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import java.util.Date
import java.util.Locale


object DateHelper {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    fun startOfTheWeek(date: LocalDate): LocalDate {
        return if (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY) {
            date.with(TemporalAdjusters.next(DayOfWeek.MONDAY)) // Start of next week
        } else {
            date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) // Start of current week
        }
    }

    fun endOfTheWeek(date: LocalDate): LocalDate {
        return if (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY) {
            date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)) // Start of next week
        } else {
            date.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)) // Start of current week
        }
    }

    fun generateDaysOfTheWeek(start: LocalDate = LocalDate.now()): MutableList<Map<String, String>> {
        val currentWeekStart = startOfTheWeek(start)
        val weekData = mutableListOf<Map<String, String>>()

        for (i in 0 until 5) {
            val date = currentWeekStart.plusDays(i.toLong())
            val formattedDate = date.format(DateTimeFormatter.ofPattern("dd"))
            val weekday = date.dayOfWeek.name.toTitleCase()

            val entry = mapOf(
                "date" to formattedDate,
                "weekday" to weekday.slice(IntRange(0, 2)),
                "weekdayFull" to weekday,
                "fullDate" to date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            )
            weekData.add(entry)
        }

        return weekData
    }

    fun getToday(pattern: String = "yyyy-MM-dd"): String {
        val today = LocalDate.now()
        return today.format(DateTimeFormatter.ofPattern(pattern))
    }

    fun checkConflicts(list: List<ClassSlot>, date: String, hour: Int, duration: Int): Boolean {
        val selectedDateTime = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val selectedHourRange = hour until (hour + duration)

        return list.any { slot ->
            val slotDateTime = LocalDate.parse(slot.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val slotHourRange = slot.startingHour until (slot.startingHour + slot.noOfHours)
            val isSameDate = selectedDateTime == slotDateTime
            val isOverlapping = selectedHourRange.intersect(slotHourRange).isNotEmpty()

            isSameDate && isOverlapping
        }
    }

    fun getDates(
        mStartD: Date?,
        calendarList: ArrayList<CalendarData>,
        calendarAdapter: CalendarAdapter,
        binding: FragmentHomeBinding
    ) {
        val dateList = ArrayList<CalendarData>()
        val dates = ArrayList<Date>()
        val calendar = Calendar.getInstance()

        val maxDaysToShow = 100

        calendar.time = mStartD ?: Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)

        val prevMonthDays = firstDayOfMonth - 1
        val totalDays = maxDaysToShow - prevMonthDays

        calendar.add(Calendar.DATE, -prevMonthDays)

        for (i in 0 until totalDays) {
            dates.add(calendar.time)
            dateList.add(CalendarData(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        calendarList.clear()
        calendarList.addAll(dateList)
        calendarAdapter.updateData(dateList)

        for (item in dateList.indices) {
            if (dateList[item].data == mStartD) {
                calendarAdapter.setPosition(item)
                binding.calendarView.scrollToPosition(item - 2)
            }
        }
    }

    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }

    fun toDate(date: String?): LocalDate? {
        return try {
            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            date?.let { LocalDate.parse(it, pattern) }
        } catch (e: Exception) {
            null
        }
    }

    fun getSmartDate(dateString: String): String {
        val currentDate = LocalDate.now()

        val inputDate = dateFormat.parse(dateString) ?: return dateString

        val localDate = inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        val differenceInDays = currentDate.toEpochDay() - localDate.toEpochDay()

        val output = when {
            differenceInDays == 0L -> "Today"
            differenceInDays == 1L -> "Yesterday"
            differenceInDays == -1L -> "Tomorrow"
            differenceInDays > 1L -> "$differenceInDays days ago"
            else -> "in ${-differenceInDays} days"
        }

        val longDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val fullDate = inputDate.let { longDateFormat.format(it) }

        return "$output - $fullDate"
    }
}
