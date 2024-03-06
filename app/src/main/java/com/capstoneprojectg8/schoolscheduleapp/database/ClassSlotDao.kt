package com.capstoneprojectg8.schoolscheduleapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.capstoneprojectg8.schoolscheduleapp.models.ScheduleSlot

@Dao
interface ClassSlotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  addClassSlot(classSlot: ScheduleSlot)

    @Update
    suspend fun editClassSlot(classSlot: ScheduleSlot)

    @Delete
    suspend fun deleteClassSlot(classSlot: ScheduleSlot)

    @Query("SELECT * FROM CLASS_SLOTS ORDER BY id DESC")
    fun getAllClassSlots(): LiveData<List<ScheduleSlot>>
}