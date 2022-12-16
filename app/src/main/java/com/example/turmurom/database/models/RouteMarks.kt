package com.example.turmurom.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "RouteMarks",
    foreignKeys = [ForeignKey(
        entity = Route::class,
        childColumns = ["RouteId"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE                  //Каскадное удаление
    ), ForeignKey(
        entity = Mark::class,
        childColumns = ["MarkId"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE                  //Каскадное удаление
    )])
data class RouteMarks (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "RouteId")
    var routeId: Int,
    @ColumnInfo(name = "MarkId")
    var markId: Int,
    @ColumnInfo(name = "Index")
    var index: Int
)