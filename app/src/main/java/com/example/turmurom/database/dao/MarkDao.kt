package com.example.turmurom.database.dao

import androidx.room.*
import com.example.turmurom.database.models.Category
import com.example.turmurom.database.models.ElectedMarks
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

//    @Transaction
//    @Query("SELECT * FROM Marks")
//    suspend fun getAllMarksWithPhotos(): List<MarksWithPhotos>

    @Transaction
    @Query("SELECT ab.id, ab.Title, ab.Description, ab.CategoryId, ab.Address, " +
            "CASE WHEN (SELECT 1 FROM UserElected WHERE UserId = :userId AND MarkId = ab.id) = 1 THEN 1 ELSE 0 END AS Elected " +
            "FROM Marks ab INNER JOIN MarkPhotos gf ON gf.MarkId = ab.id " +
            "GROUP BY ab.id, ab.Title, gf.Photo")
    suspend fun getAllMarksWithPhotos(userId: Int): List<MarksWithPhotos>

    @Transaction
    @Query("Select bc.id, bc.Title, bc.Description, ef.id AS CategoryId, ef.Title AS Category, bc.Address, CASE WHEN ab.Photo " +
            "IS NOT NULL THEN (SELECT phot.Photo FROM MarkPhotos phot WHERE phot.MarkId = bc.id) END Photo " +
            "From MarkPhotos ab INNER JOIN Marks bc ON ab.MarkId = bc.id " +
            "INNER JOIN UserElected cd ON cd.MarkId = bc.id " +
            "INNER JOIN Identity de ON cd.UserId = de.id " +
            "INNER JOIN Categories ef ON ef.id = bc.CategoryId WHERE de.id = :userId " +
            "GROUP BY bc.id, bc.Title, bc.Description, ef.Title, bc.Address")
    suspend fun getElectedMarksWithPhoto(userId: Int): List<ElectedMarks>

    @Query("SELECT * FROM Categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM Marks WHERE categoryId IN (:categoryIds) ")
    fun getMarkByCategoryId(categoryIds: String): Flow<List<Mark>>

    @Query("SELECT * FROM Marks WHERE id = :id")
    fun getMarkById(id: Int): Mark

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