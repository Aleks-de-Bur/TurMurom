package com.example.turmurom.database.dao

import androidx.room.*
import com.example.turmurom.database.models.Excursion
import kotlinx.coroutines.flow.Flow

@Dao
interface ExcursionDao {
    @Query("SELECT * FROM Excursions")
    fun getAllExcursions(): Flow<List<Excursion>>

    @Query("SELECT * FROM Excursions WHERE GuideId = :id")
    fun getAllExcursionsForGuide(id: Int): Flow<List<Excursion>>

//    @Insert
//    fun insertExcursion(excursion: Excursion)
//
//    @Update
//    fun update(excursion: Excursion)
//
//    @Delete
//    fun deleteExcursion(excursion: Excursion)
}