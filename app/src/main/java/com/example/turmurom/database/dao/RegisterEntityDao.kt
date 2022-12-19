package com.example.turmurom.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.turmurom.database.models.Mark
import com.example.turmurom.database.models.RegisterEntity
import com.example.turmurom.database.models.UserElected
import kotlinx.coroutines.flow.Flow

@Dao
interface RegisterEntityDao {
    @Insert
    suspend fun insertEntity(register: RegisterEntity)

    @Query("SELECT * FROM Identity ORDER BY id DESC")
    fun getAllUsers(): LiveData<List<RegisterEntity>>

    @Query("SELECT * FROM Identity WHERE Email LIKE :email")
    suspend fun getUsername(email: String): RegisterEntity?

    @Query("SELECT * FROM Identity WHERE id LIKE :id")
    fun getUserById(id: Int): RegisterEntity

    @Query("SELECT * FROM Identity WHERE Email LIKE :email AND Password LIKE :password")
    suspend fun getUserId(email: String, password: String): RegisterEntity?

    @Query("SELECT * FROM Marks WHERE id = :markId")
    fun selectMarksForVisOrElect(markId: Int): Flow<List<Mark>>

    @Insert
    fun insertElectedMark(userElected: UserElected)

    @Query("DELETE FROM UserElected WHERE MarkId = :markId AND UserId = :userId")
    fun deleteElectedMark(markId: Int, userId: Int)
}