package com.example.turmurom.database.dao

import androidx.room.*
import com.example.turmurom.database.models.ExcursionPhoto
import com.example.turmurom.database.models.MarkPhoto
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkPhotoDao {
    @Query("SELECT * FROM MarkPhotos")
    fun getAll(): Flow<List<MarkPhoto>>

    @Query("SELECT * FROM MarkPhotos WHERE MarkId = :id")
    fun getMarkPhotosById(id: Int): List<MarkPhoto>

    @Query("SELECT * FROM MarkPhotos WHERE MarkId = :id LIMIT 1")
    fun getMarkPhotoById(id: Int): MarkPhoto

    @Insert
    fun insert(markPhoto: MarkPhoto)

    @Update
    fun update(markPhoto: MarkPhoto)

    @Delete
    fun delete(markPhoto: MarkPhoto)
}