package com.example.turmurom.database.dao

import androidx.room.*
import com.example.turmurom.database.models.Excursion
import com.example.turmurom.database.models.ExcursionWithPhoto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExcursionDao {
    @Query("SELECT * FROM Excursions")
    fun getAllExcursions(): Flow<List<Excursion>>

    @Transaction
    @Query("SELECT * FROM Excursions WHERE GuideId = :id")
    suspend fun getAllExcursionsForGuide(id: Int): List<ExcursionWithPhoto>

    @Transaction
    @Query("SELECT * FROM Excursions")
    suspend fun getAllExcursionsWithPhotos(): List<ExcursionWithPhoto>

//    @Insert
//    fun insertExcursion(excursion: Excursion)
//
//    @Update
//    fun update(excursion: Excursion)
//
//    @Delete
//    fun deleteExcursion(excursion: Excursion)
}