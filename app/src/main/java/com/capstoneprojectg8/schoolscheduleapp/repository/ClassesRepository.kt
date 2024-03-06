package com.capstoneprojectg8.schoolscheduleapp.repository

import androidx.lifecycle.LiveData
import com.capstoneprojectg8.schoolscheduleapp.database.ClassesDatabase
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import com.capstoneprojectg8.schoolscheduleapp.models.ScheduleSlot

class ClassesRepository(private val db: ClassesDatabase) {

    suspend fun addClass(classes: Class) = db.getClassesDao().addClass(classes)

    suspend fun editClass(classes: Class) = db.getClassesDao().editClass(classes)

    suspend fun deleteClass(classes: Class) = db.getClassesDao().deleteClass(classes)

    suspend fun addAssignment(assignment: Assignment) = db.getClassesDao().addAssignment(assignment)

    suspend fun editAssignment(assignment: Assignment) = db.getClassesDao().editAssignment(assignment)

    suspend fun deleteAssignment(assignment: Assignment) =db.getClassesDao().deleteAssignment(assignment)

    fun getAllClasses() = db.getClassesDao().getAllClasses()

    fun getAssignmentListByClass(id: Int): LiveData<List<Assignment>> = db.getClassesDao().getAssignmentListByClass(id)

    fun getDefaultListValue(id: Int): LiveData<Class> = db.getClassesDao().getDefaultListValue(id)

    suspend fun addClassSlot(classSlot: ScheduleSlot) = db.getClassSlotDao().addClassSlot(classSlot)
    suspend fun editClassSlot(classSlot: ScheduleSlot) = db.getClassSlotDao().editClassSlot(classSlot)
    suspend fun deleteClassSlot(classSlot: ScheduleSlot) = db.getClassSlotDao().deleteClassSlot(classSlot)
    fun getAllClassSlots() = db.getClassSlotDao().getAllClassSlots()
}