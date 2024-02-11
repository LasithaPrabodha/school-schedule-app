package com.capstoneprojectg8.schoolscheduleapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstoneprojectg8.schoolscheduleapp.model.Class

@Database(entities = [Class::class], version = 1)
abstract class ClassesDatabase : RoomDatabase() {

    abstract fun getClassesDao(): ClassesDao

    companion object {
        @Volatile
        private var instance: ClassesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDatabase(context).also {
                        instance = it
                    }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ClassesDatabase::class.java,
                "class_db"
            ).build()
    }
}
