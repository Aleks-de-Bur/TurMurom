package com.example.turmurom.database.dao

import androidx.room.*
import com.example.turmurom.database.models.Guide
import kotlinx.coroutines.flow.Flow

@Dao
interface GuideDao {
    @Query("SELECT * FROM Guides")
    fun getAll(): Flow<List<Guide>>

    @Query("SELECT * FROM Guides Where id = :id LIMIT 1")
    fun getGuide(id: Int): Guide?

    @Insert
    fun insert(guide: Guide)

    @Update
    fun update(guide: Guide)

    @Delete
    fun delete(guide: Guide)
}