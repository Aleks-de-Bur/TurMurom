package com.example.turmurom.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.turmurom.database.models.Schedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM Schedule WHERE MarkId = :id")
    fun getAllScheduleForMark(id: Int): Flow<List<Schedule>>
}