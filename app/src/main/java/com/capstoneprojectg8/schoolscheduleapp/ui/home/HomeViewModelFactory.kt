package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository

class HomeViewModelFactory(val app: Application, private val classesRepository: ClassesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(app, classesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}