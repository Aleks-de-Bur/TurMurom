package com.example.turmurom.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "UserVisited",
    foreignKeys = [ForeignKey(
        entity = RegisterEntity::class,
        childColumns = ["UserId"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE                  //Каскадное удаление
    ), ForeignKey(
        entity = Mark::class,
        childColumns = ["MarkId"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE                  //Каскадное удаление
    )]
)
data class UserVisited (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "UserId")
    var userId: Int,
    @ColumnInfo(name = "MarkId")
    var markId: Int,
    @ColumnInfo(name = "Visited")
    var visited: Boolean
)