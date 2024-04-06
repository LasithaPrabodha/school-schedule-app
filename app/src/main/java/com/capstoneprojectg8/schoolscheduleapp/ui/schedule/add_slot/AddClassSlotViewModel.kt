package com.capstoneprojectg8.schoolscheduleapp.ui.schedule.add_slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstoneprojectg8.schoolscheduleapp.database.repository.ClassRepository
import com.capstoneprojectg8.schoolscheduleapp.models.SClass
import com.capstoneprojectg8.schoolscheduleapp.models.ClassSlot
import com.capstoneprojectg8.schoolscheduleapp.utils.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddClassSlotViewModel @Inject constructor(private val classRepository: ClassRepository) :
    ViewModel() {

    private val _classes = MutableLiveData<List<SClass>>()
    val classes: LiveData<List<SClass>> = _classes
    fun getAllClasses() = classRepository.getAllClasses()

    suspend fun addClassSlot(classSlot: ClassSlot) {
        classRepository.addClassSlot(classSlot.toEntity())
    }


    fun getAllClassSlots() =
        classRepository.getAllClassSlots()

}