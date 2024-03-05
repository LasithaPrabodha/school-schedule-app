package com.capstoneprojectg8.schoolscheduleapp.ui.schedule.add_slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstoneprojectg8.schoolscheduleapp.models.ScheduleSlot
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository

class AddClassSlotViewModel(private val classesRepository: ClassesRepository) : ViewModel() {

    private val _classes = MutableLiveData<List<Class>>()
    val classes: LiveData<List<Class>> = _classes
    fun getAllClasses() = classesRepository.getAllClasses()

    suspend fun addClassSlot(classSlot: ScheduleSlot){
        classesRepository.addClassSlot(classSlot)
    }

}