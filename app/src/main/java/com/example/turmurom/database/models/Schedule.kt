package com.example.turmurom.database.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.time.LocalTime

@Entity(
    tableName = "Schedule",
    foreignKeys = [ForeignKey(
        entity = Mark::class,
        childColumns = ["MarkId"],
        parentColumns = ["id"],
        onDelete = CASCADE                  //Каскадное удаление
    )]
)
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "MarkId")
    var markId: Int,
    @ColumnInfo(name = "Day")
    var day: Int,
    @ColumnInfo(name = "Start")
    var start: String,
    @ColumnInfo(name = "End")
    var end: String
)
