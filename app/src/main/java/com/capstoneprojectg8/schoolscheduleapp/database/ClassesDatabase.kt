package com.capstoneprojectg8.schoolscheduleapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstoneprojectg8.schoolscheduleapp.database.dao.ClassSlotDao
import com.capstoneprojectg8.schoolscheduleapp.database.dao.ClassDao
import com.capstoneprojectg8.schoolscheduleapp.database.entities.AssignmentEntity
import com.capstoneprojectg8.schoolscheduleapp.database.entities.ClassEntity
import com.capstoneprojectg8.schoolscheduleapp.database.entities.ClassSlotEntity

@Database(
    entities = [
        ClassEntity::class,
        AssignmentEntity::class,
        ClassSlotEntity::class
    ],
    version = 10
)
abstract class ClassesDatabase : RoomDatabase() {

    abstract fun getClassesDao(): ClassDao
    abstract fun getClassSlotDao(): ClassSlotDao

}
