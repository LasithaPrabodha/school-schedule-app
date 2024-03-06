package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.addassignment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository


class AddNewAssignmentViewModelProvider(val app: Application, private val classesRepository: ClassesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNewAssignmentViewModel::class.java)) {
            return AddNewAssignmentViewModel(app, classesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}