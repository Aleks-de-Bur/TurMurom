package com.example.turmurom.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "ExcursionPhotos",
    foreignKeys = [ForeignKey(
        entity = Excursion::class,
        childColumns = ["ExcursionId"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE                  //Каскадное удаление
    )])
data class ExcursionPhoto (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "ExcursionId")
    var excursionId: Int,
    @ColumnInfo(name = "Photo")
    var pathPhoto: String
)