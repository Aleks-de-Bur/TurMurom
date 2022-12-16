package com.example.turmurom.database.dao

import androidx.room.*
import com.example.turmurom.database.models.Route
import com.example.turmurom.database.models.RouteMarks
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {
    @Query("SELECT * FROM Routes")
    fun getAllRoutes(): Flow<List<Route>>

    @Query("SELECT * FROM RouteMarks WHERE RouteId = :id")
    fun getRouteMarksById(id: Int): List<RouteMarks>

    @Insert
    fun insertRoute(route: Route)

    @Update
    fun updateRoute(route: Route)

    @Delete
    fun deleteRoute(route: Route)
}