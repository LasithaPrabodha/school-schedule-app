package com.capstoneprojectg8.schoolscheduleapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.capstoneprojectg8.schoolscheduleapp.database.relations.ClassWithAssignments
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.Class

@Dao
interface ClassesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addClass(classes: Class)

    @Update
    suspend fun editClass(classes: Class)

    @Delete
    suspend fun deleteClass(classes: Class)

    @Query("SELECT * FROM CLASSES ORDER BY id ASC")
    fun getAllClasses(): LiveData<List<Class>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAssignment(assignment: Assignment)

    @Update
    suspend fun editAssignment(assignment: Assignment)

    @Delete
    suspend fun deleteAssignment(assignment: Assignment)

    @Query("SELECT * FROM ASSIGNMENT ORDER BY id ASC")
    fun getAllAssignments(): LiveData<List<Assignment>>

    @Transaction
    @Query("SELECT * FROM ASSIGNMENT WHERE classId = :id")
    fun getAssignmentListByClass(id: Int): LiveData<List<Assignment>>
}