package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.AppDispatchers
import com.capstoneprojectg8.schoolscheduleapp.database.repository.ClassRepository
import kotlinx.coroutines.launch
import com.capstoneprojectg8.schoolscheduleapp.models.SClass
import com.capstoneprojectg8.schoolscheduleapp.utils.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassSettingsViewModel @Inject constructor(
    private val classRepository: ClassRepository,
    private val appDispatchers: AppDispatchers
) :
    ViewModel() {

    fun addClass(newSClass: SClass) {
        viewModelScope.launch(appDispatchers.io) {
            classRepository.addClass(newSClass.toEntity())
        }
    }

    fun editClass(editingSClass: SClass) {
        viewModelScope.launch(appDispatchers.io) {
            classRepository.editClass(editingSClass.toEntity())
        }
    }

    fun deleteClass(deletingSClass: SClass) {
        viewModelScope.launch(appDispatchers.io) {
            classRepository.deleteClass(deletingSClass.toEntity())
        }
    }

    fun getAllClasses() = classRepository.getAllClasses()

    fun getAllClassSlots() = classRepository.getAllClassSlots()

}