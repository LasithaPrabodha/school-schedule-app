package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.model.Class
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository
import kotlinx.coroutines.launch

class ClassesViewModel(val app: Application, private val classesRepository: ClassesRepository) : ViewModel() {

    fun addClass(classes: Class) {
        viewModelScope.launch {
            classesRepository.addClass(classes)
        }
    }

    fun editClass(classes: Class) {
        viewModelScope.launch {
            classesRepository.editClass(classes)
        }
    }

    fun deleteClass(classes: Class) {
        viewModelScope.launch {
            classesRepository.deleteClass(classes)
        }
    }

    fun getAllClasses() = classesRepository.getAllClasses()

}