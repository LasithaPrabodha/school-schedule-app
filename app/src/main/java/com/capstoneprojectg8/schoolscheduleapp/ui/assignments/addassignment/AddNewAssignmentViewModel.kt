package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.addassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.database.repository.ClassRepository
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.utils.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewAssignmentViewModel @Inject constructor(
    private val classRepository: ClassRepository
) : ViewModel() {

    fun addAssignment(assignment: Assignment) {
        viewModelScope.launch(Dispatchers.IO) {
            classRepository.addAssignment(assignment.toEntity())
        }
    }

    fun editAssignment(assignment: Assignment) {
        viewModelScope.launch(Dispatchers.IO) {
            classRepository.editAssignment(assignment.toEntity())
        }
    }

    fun getDefaultListValue(id: Int) = classRepository.getDefaultListValue(id)

}