package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.database.repository.ClassRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import com.capstoneprojectg8.schoolscheduleapp.utils.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassSettingsViewModel @Inject constructor(private val classRepository: ClassRepository) :
    ViewModel() {

    fun addClass(newClass: Class) {
        viewModelScope.launch(Dispatchers.IO) {
            classRepository.addClass(newClass.toEntity())
        }
    }

    fun editClass(editingClass: Class) {
        viewModelScope.launch(Dispatchers.IO) {
            classRepository.editClass(editingClass.toEntity())
        }
    }

    fun deleteClass(deletingClass: Class) {
        viewModelScope.launch(Dispatchers.IO) {
            classRepository.deleteClass(deletingClass.toEntity())
        }
    }

    fun getAllClasses() = classRepository.getAllClasses()

    fun getAllClassSlots() = classRepository.getAllClassSlots()

}