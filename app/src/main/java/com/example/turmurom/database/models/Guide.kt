package com.example.turmurom.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "Guides",
    indices = [Index(value = ["FirstName", "LastName", "Patronymic"],
        unique = true)])
data class Guide (
    @PrimaryKey(autoGenerate = true,)
    var id: Int? = null,
    @ColumnInfo(name = "FirstName")
    var firstName: String,
    @ColumnInfo(name = "LastName")
    var lastName: String,
    @ColumnInfo(name = "Patronymic")
    var patronymic: String,
    @ColumnInfo(name = "EMail")
    var eMail: String,
    @ColumnInfo(name = "TelNumber")
    var telNumber: String,
    @ColumnInfo(name = "Photo")
    var pathPhoto: String
)