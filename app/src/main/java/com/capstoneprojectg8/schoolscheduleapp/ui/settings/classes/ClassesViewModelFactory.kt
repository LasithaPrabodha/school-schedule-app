package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository

class ClassesViewModelFactory(val app:Application ,private val classesRepository: ClassesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ClassesViewModel(app, classesRepository) as T


    }
}