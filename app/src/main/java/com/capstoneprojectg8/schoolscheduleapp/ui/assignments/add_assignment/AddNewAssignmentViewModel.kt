package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.add_assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.AppDispatchers
import com.capstoneprojectg8.schoolscheduleapp.database.repository.ClassRepository
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.utils.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddNewAssignmentViewModel @Inject constructor(
    private val classRepository: ClassRepository,
    private val appDispatchers: AppDispatchers
) : ViewModel() {

    suspend fun addAssignment(assignment: Assignment) = withContext(appDispatchers.io) {
        classRepository.addAssignment(assignment.toEntity())
    }

    suspend fun editAssignment(assignment: Assignment) = withContext(appDispatchers.io) {
        classRepository.editAssignment(assignment.toEntity())
    }

    fun getClassSlotById(id: Int) = classRepository.getClassSlotById(id)

}