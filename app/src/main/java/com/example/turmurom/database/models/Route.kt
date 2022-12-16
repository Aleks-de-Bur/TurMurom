package com.example.turmurom.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Routes")
data class Route (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "Title", index = true)
    var name: String,
    @ColumnInfo(name = "Description")
    var description: String,
    @ColumnInfo(name = "Duration")
    var duration: String,
    @ColumnInfo(name = "Photo")
    var pathPhoto: String
)