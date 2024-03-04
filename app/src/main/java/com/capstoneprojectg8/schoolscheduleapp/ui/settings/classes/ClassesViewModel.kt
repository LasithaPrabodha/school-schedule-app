package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClassesViewModel(val app: Application, private val classesRepository: ClassesRepository) : ViewModel() {

    fun addClass(classes: Class) {
        viewModelScope.launch(Dispatchers.IO) {
            classesRepository.addClass(classes)
        }
    }

    fun editClass(classes: Class) {
        viewModelScope.launch(Dispatchers.IO) {
            classesRepository.editClass(classes)
        }
    }

    fun deleteClass(classes: Class) {
        viewModelScope.launch(Dispatchers.IO) {
            classesRepository.deleteClass(classes)
        }
    }

    fun getAllClasses() = classesRepository.getAllClasses()

}