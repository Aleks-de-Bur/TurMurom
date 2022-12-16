package com.example.turmurom.repositories

import android.util.Log
import com.example.turmurom.database.dao.RegisterEntityDao
import com.example.turmurom.database.models.RegisterEntity

class RegisterRepository(private val dao: RegisterEntityDao) {

    val users = dao.getAllUsers()
    suspend fun insert(user: RegisterEntity) {
        return dao.insertEntity(user)
    }

    suspend fun getUserName(userName: String):RegisterEntity?{
        Log.i("MYTAG", "inside Repository Getusers fun ")
        return dao.getUsername(userName)
    }

    suspend fun getUserId(userName: String, userPassword: String):RegisterEntity?{
        return dao.getUserId(userName, userPassword)
    }
    //suspend fun deleteAll(): Int {
    //    return dao.deleteAll()
    //}

}