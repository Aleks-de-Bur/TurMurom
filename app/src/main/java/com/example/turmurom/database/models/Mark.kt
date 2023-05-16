package com.example.turmurom.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Marks",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        childColumns = ["CategoryId"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE                  //Каскадное удаление
    )]
)
data class Mark(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "Title", index = true)
    var title: String,
    @ColumnInfo(name = "Description")
    var description: String,
    @ColumnInfo(name = "CategoryId")
    var categoryId: Int,
    @ColumnInfo(name = "Address")
    var address: String,
//    @ColumnInfo(name = "AxisX")
//    var axisx: String,
//    @ColumnInfo(name = "AxisY")
//    var axisy: String,
    @ColumnInfo(name = "Elected")
    var elected: Boolean
)