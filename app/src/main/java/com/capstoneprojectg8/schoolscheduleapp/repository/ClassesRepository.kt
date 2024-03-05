package com.capstoneprojectg8.schoolscheduleapp.repository

import com.capstoneprojectg8.schoolscheduleapp.database.ClassesDatabase
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import com.capstoneprojectg8.schoolscheduleapp.models.ScheduleSlot

class ClassesRepository(private val db: ClassesDatabase) {

    suspend fun addClass(classes: Class) = db.getClassesDao().addClass(classes)
    suspend fun editClass(classes: Class) = db.getClassesDao().editClass(classes)
    suspend fun deleteClass(classes: Class) = db.getClassesDao().deleteClass(classes)

    fun getAllClasses() = db.getClassesDao().getAllClasses()

    suspend fun addClassSlot(classSlot: ScheduleSlot) = db.getClassSlotDao().addClassSlot(classSlot)
    suspend fun editClassSlot(classSlot: ScheduleSlot) = db.getClassSlotDao().editClassSlot(classSlot)
    suspend fun deleteClassSlot(classSlot: ScheduleSlot) = db.getClassSlotDao().deleteClassSlot(classSlot)
    fun getAllClassSlots() = db.getClassSlotDao().getAllClassSlots()
}