package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository
import com.capstoneprojectg8.schoolscheduleapp.models.Class

class HomeViewModel(val app: Application, private val classesRepository: ClassesRepository) : ViewModel() {


    private var _classes = MutableLiveData<List<Class>>()
    val classes: LiveData<List<Class>> = _classes

    fun getAllClasses() = classesRepository.getAllClasses()

}