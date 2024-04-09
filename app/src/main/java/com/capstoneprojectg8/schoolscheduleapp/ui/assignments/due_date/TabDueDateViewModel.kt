package com.capstoneprojectg8.schoolscheduleapp.ui.assignments.due_date

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.capstoneprojectg8.schoolscheduleapp.AppDispatchers
import com.capstoneprojectg8.schoolscheduleapp.database.repository.ClassRepository
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.ClassWithAssignments
import com.capstoneprojectg8.schoolscheduleapp.models.ClassWithAssignmentsByDate
import com.capstoneprojectg8.schoolscheduleapp.utils.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TabDueDateViewModel @Inject constructor(
    private val classRepository: ClassRepository,
    private val appDispatchers: AppDispatchers
) : ViewModel() {

    fun filterAssignmentsByDueDate(): LiveData<List<ClassWithAssignmentsByDate>> {
        return classRepository.getAllAssignmentsWithClasses()
            .map { list -> list.filter { it.assignments.isNotEmpty() } }
            .map { classAssignments ->

                // Flatten the list of ClassWithAssignments and Assignment objects into pairs of
                // (dueDate, Pair(sClass, assignment))
                val pairsByDate = classAssignments.flatMap { classWithAssignments ->
                    classWithAssignments.assignments.map { assignment ->
                        Pair(
                            assignment.dueDate,
                            Pair(classWithAssignments.sclass, assignment)
                        )
                    }
                }

                // Group the pairs by dueDate
                val groupedByDate = pairsByDate.groupBy({ it.first }, { it.second })


                // Map the grouped pairs into ClassWithAssignmentsByDate objects
                val result = groupedByDate.map { (date, assignmentsBySubject) ->
                    // For each dueDate group, collect assignments by subject
                    val classWithAssignmentsList = assignmentsBySubject
                        .groupBy { it.first } // Group assignments by subject (sClass)
                        .map { (sclass, assignments) ->
                            // Create ClassWithAssignments object for each subject with merged assignments
                            ClassWithAssignments(
                                sclass,
                                assignments.map { it.second } // Collect assignments for the subject
                            )
                        }

                    // Create ClassWithAssignmentsByDate object for the current dueDate
                    ClassWithAssignmentsByDate(date, classWithAssignmentsList)
                }


                result.sortedBy { it.date }
            }
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