package com.example.turmurom.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "MarkPhotos",
    foreignKeys = [ForeignKey(
        entity = Mark::class,
        childColumns = ["MarkId"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE                  //Каскадное удаление
    )])
data class MarkPhoto (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "MarkId")
    var markId: Int,
    @ColumnInfo(name = "Photo")
    var pathPhoto: String
)