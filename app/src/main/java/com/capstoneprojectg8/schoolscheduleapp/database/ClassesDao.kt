package com.capstoneprojectg8.schoolscheduleapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.capstoneprojectg8.schoolscheduleapp.models.Class

@Dao
interface ClassesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  addClass(classes: Class)

    @Update
    suspend fun editClass(classes: Class)

    @Delete
    suspend fun deleteClass(classes: Class)

    @Query("SELECT * FROM CLASSES ORDER BY id DESC")
    fun getAllClasses(): LiveData<List<Class>>
}