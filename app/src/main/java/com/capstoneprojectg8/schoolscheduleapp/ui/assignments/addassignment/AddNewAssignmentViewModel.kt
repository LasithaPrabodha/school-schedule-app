package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.addassignment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNewAssignmentViewModel(val app: Application, private val classesRepository: ClassesRepository) : ViewModel() {

    fun addAssignment(assignment: Assignment){
        viewModelScope.launch(Dispatchers.IO) {
            classesRepository.addAssignment(assignment)
        }
    }

    fun editAssignment(assignment: Assignment){
        viewModelScope.launch(Dispatchers.IO) {
            classesRepository.editAssignment(assignment)
        }
    }



}