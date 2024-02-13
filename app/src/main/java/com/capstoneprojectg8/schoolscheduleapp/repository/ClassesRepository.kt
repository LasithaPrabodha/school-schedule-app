package com.capstoneprojectg8.schoolscheduleapp.repository

import com.capstoneprojectg8.schoolscheduleapp.database.ClassesDatabase
import com.capstoneprojectg8.schoolscheduleapp.models.Class

class ClassesRepository(private val db: ClassesDatabase) {

    suspend fun addClass(classes: Class) = db.getClassesDao().addClass(classes)
    suspend fun editClass(classes: Class) = db.getClassesDao().editClass(classes)
    suspend fun deleteClass(classes: Class) = db.getClassesDao().deleteClass(classes)

    fun getAllClasses() = db.getClassesDao().getAllClasses()
}