package com.capstoneprojectg8.schoolscheduleapp.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.repository.ClassesRepository
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel(val app: Application, private val classesRepository: ClassesRepository) : ViewModel() {


    private var _classes = MutableLiveData<List<Class>>()
    val classes: LiveData<List<Class>> = _classes

    fun getAllClasses() = classesRepository.getAllClasses()

    fun getAssignmentListByClassId(classId: Int) = classesRepository.getAssignmentListByClass(classId)

    fun deleteAssignment(assignment: Assignment){
        viewModelScope.launch(Dispatchers.IO) {
            classesRepository.deleteAssignment(assignment)
        }
    }

    fun editAssignment(assignment: Assignment){
        viewModelScope.launch(Dispatchers.IO) {
            classesRepository.editAssignment(assignment)
        }
    }

}