package com.example.turmurom.database.dao

import androidx.room.*
import com.example.turmurom.database.models.ExcursionPhoto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExcursionPhotoDao {
    @Query("SELECT * FROM ExcursionPhotos")
    fun getAll(): Flow<List<ExcursionPhoto>>

    @Query("SELECT * FROM ExcursionPhotos WHERE ExcursionId = :id")
    fun getExcursionPhotosById(id: Int): List<ExcursionPhoto>

    @Query("SELECT * FROM ExcursionPhotos WHERE ExcursionId = :id LIMIT 1")
    fun getExcursionPhotoById(id: Int): ExcursionPhoto

    @Insert
    fun insert(excursionPhoto: ExcursionPhoto)

    @Update
    fun update(excursionPhoto: ExcursionPhoto)

    @Delete
    fun delete(excursionPhoto: ExcursionPhoto)
}