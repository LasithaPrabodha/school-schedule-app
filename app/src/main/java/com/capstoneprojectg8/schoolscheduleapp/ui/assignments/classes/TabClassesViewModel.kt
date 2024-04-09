package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.AppDispatchers
import com.capstoneprojectg8.schoolscheduleapp.database.repository.ClassRepository
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.ClassWithAssignments
import com.capstoneprojectg8.schoolscheduleapp.utils.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabClassesViewModel @Inject constructor(
    private val classRepository: ClassRepository,
    private val appDispatchers: AppDispatchers
) : ViewModel() {
    fun getAllAssignmentsWithClasses() =
        classRepository.getAllAssignmentsWithClasses()
            .map { list -> list.filter { it.assignments.isNotEmpty() } }

    fun getAllAssignmentsWithClassesPrioritized(): LiveData<List<ClassWithAssignments>> {
        return getAllAssignmentsWithClasses().map { list ->
            list.map {
                ClassWithAssignments(
                    it.sclass,
                    it.assignments.filter { a -> a.isPriority }
                )
            }
        }.map { list -> list.filter { ca -> ca.assignments.isNotEmpty() } }
    }


    fun deleteAssignment(assignment: Assignment) {
        viewModelScope.launch(appDispatchers.io) {
            classRepository.deleteAssignment(assignment.toEntity())
        }
    }

    fun editAssignment(assignment: Assignment) {
        viewModelScope.launch(appDispatchers.io) {
            classRepository.editAssignment(assignment.toEntity())
        }
    }

}