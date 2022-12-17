package com.example.turmurom.database.dao

import androidx.room.*
import com.example.turmurom.database.models.Category
import com.example.turmurom.database.models.Mark
import com.example.turmurom.database.models.MarksWithPhotos
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkDao {
    @Query("SELECT * FROM Marks")
    fun getAllMarks(): Flow<List<Mark>>

    /**
     * Единоразово получает список всех достопримечательностей
     */
    @Query("SELECT * FROM Marks")
    suspend fun getAllMarksSuspend(): List<Mark>

    @Transaction
    @Query("SELECT * FROM Marks")
    suspend fun getAllMarksWithPhotos(): List<MarksWithPhotos>

    @Query("SELECT * FROM Categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM Marks WHERE categoryId IN (:categoryIds) ")
    fun getMarkByCategoryId(categoryIds: String): Flow<List<Mark>>

    @Query("SELECT * FROM Marks WHERE id = :id")
    fun getMarkById(id: Int): Mark?

    @Query("SELECT * FROM Marks WHERE Title = :searchQuery")
    fun searchMark(searchQuery: String): Flow<List<Mark>>

    @Query("SELECT * FROM Marks ORDER BY id ASC")
    fun readData(): Flow<List<Mark>>

    @Insert
    fun insertMark(mark: Mark)

    @Update
    fun updateMark(mark: Mark)

    @Delete
    fun deleteMark(mark: Mark)
}