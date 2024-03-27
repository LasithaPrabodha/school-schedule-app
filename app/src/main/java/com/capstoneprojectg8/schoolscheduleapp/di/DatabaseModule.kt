package com.capstoneprojectg8.schoolscheduleapp.di

import android.content.Context
import androidx.room.Room
import com.capstoneprojectg8.schoolscheduleapp.database.ClassesDatabase
import com.capstoneprojectg8.schoolscheduleapp.database.repository.ClassRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ClassesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ClassesDatabase::class.java,
            "class_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideClassDao(db: ClassesDatabase) = db.getClassesDao()

    @Singleton
    @Provides
    fun provideClassSlotDao(db: ClassesDatabase) = db.getClassSlotDao()

}