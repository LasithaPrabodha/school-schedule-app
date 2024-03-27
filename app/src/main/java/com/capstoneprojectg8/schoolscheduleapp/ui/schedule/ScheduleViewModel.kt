package com.capstoneprojectg8.schoolscheduleapp.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstoneprojectg8.schoolscheduleapp.models.HourRow
import com.capstoneprojectg8.schoolscheduleapp.database.repository.ClassRepository
import com.capstoneprojectg8.schoolscheduleapp.models.ClassSlot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val classRepository: ClassRepository) :
    ViewModel() {
    private val _classSlots = MutableLiveData<List<ClassSlot>>()
    val classSlots: LiveData<List<ClassSlot>> = _classSlots

    fun getAllClassSlots() =
        classRepository.getAllClassSlots()

    fun generateHourRows(isCurrentWeek: Boolean): MutableList<HourRow> {
        val list = mutableListOf<HourRow>()
        for (index in 0..24) {
            val hour = if (index == 0) {
                "12"
            } else if (index < 10) {
                "0${index}"
            } else if (index > 12) {
                if (index - 12 < 10) "0${(index - 12)}"
                else (index - 12).toString()
            } else {
                index.toString()
            }

            val amPm = if (index < 12) "AM" else "PM"

            list.add(
                HourRow(
                    amPm = amPm,
                    hour = hour,
                    isCurrentWeek = isCurrentWeek
                )
            )
        }
        return list
    }
}