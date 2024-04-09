package com.capstoneprojectg8.schoolscheduleapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.capstoneprojectg8.schoolscheduleapp.database.entities.ClassSlotEntity

@Dao
interface ClassSlotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  addClassSlot(classSlot: ClassSlotEntity)

    @Update
    suspend fun editClassSlot(classSlot: ClassSlotEntity)

    @Delete
    suspend fun deleteClassSlot(classSlot: ClassSlotEntity)

    @Query("SELECT * FROM CLASS_SLOTS ORDER BY id DESC")
    fun getAllClassSlots(): LiveData<List<ClassSlotEntity>>

    @Query("SELECT * FROM CLASS_SLOTS WHERE id = :id")
    fun getClassSlotById(id: Int): LiveData<ClassSlotEntity>
}