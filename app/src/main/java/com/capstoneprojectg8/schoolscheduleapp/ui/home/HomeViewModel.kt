package com.capstoneprojectg8.schoolscheduleapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.AppDispatchers
import com.capstoneprojectg8.schoolscheduleapp.database.repository.ClassRepository
import com.capstoneprojectg8.schoolscheduleapp.models.SClass
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.utils.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val classRepository: ClassRepository,
    private val appDispatchers: AppDispatchers
) :
    ViewModel() {


    private var _classes = MutableLiveData<List<SClass>>()
    val classes: LiveData<List<SClass>> = _classes


    fun getAssignmentListByClassId(classSlotId: Int) =
        classRepository.getAssignmentListByClassSlot(classSlotId)

    suspend fun deleteAssignment(assignment: Assignment) = withContext(appDispatchers.io) {
        classRepository.deleteAssignment(assignment.toEntity())
    }

    suspend fun editAssignment(assignment: Assignment) = withContext(appDispatchers.io) {
        classRepository.editAssignment(assignment.toEntity())
    }

    fun getAllClassSlots() = classRepository.getAllClassSlots()
}