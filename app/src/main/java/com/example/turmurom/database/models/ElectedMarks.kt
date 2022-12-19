package com.example.turmurom.database.models

import androidx.room.ColumnInfo

data class ElectedMarks(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "Title")
    val title: String,
    @ColumnInfo(name = "Description")
    val description: String,
    @ColumnInfo(name = "CategoryId")
    val categoryId: Int,
    @ColumnInfo(name = "Category")
    val category: String,
    @ColumnInfo(name = "Address")
    val address: String,
    @ColumnInfo(name = "Photo")
    val photo: String
)
