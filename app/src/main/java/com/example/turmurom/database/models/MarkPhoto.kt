package com.example.turmurom.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "MarkPhotos",
    foreignKeys = [ForeignKey(
        entity = Mark::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("MarkId"),
        onDelete = ForeignKey.CASCADE                  //Каскадное удаление
    )])
data class MarkPhoto (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "MarkId", index = true)
    var markId: Int,
    @ColumnInfo(name = "Photo")
    var pathPhoto: String
)