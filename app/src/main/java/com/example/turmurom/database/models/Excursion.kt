package com.example.turmurom.database.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.io.Serializable

@Entity(
    tableName = "Excursions",
    foreignKeys = [ForeignKey(
        entity = Guide::class,
        childColumns = ["GuideId"],
        parentColumns = ["id"],
        onDelete = CASCADE                  //Каскадное удаление
    )]
)
data class Excursion(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "Title", index = true)
    var name: String,
    @ColumnInfo(name = "Description", index = true)
    var description: String,
    @ColumnInfo(name = "Duration")
    var duration: String,
    @ColumnInfo(name = "Price")
    var price: String,
    @ColumnInfo(name = "GuideId")
    var guideId: Int
) : Serializable
