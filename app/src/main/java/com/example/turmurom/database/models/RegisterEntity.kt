package com.example.turmurom.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Identity",
    indices = [Index(value = ["Login"], unique = true), Index(value = ["Email"], unique = true)])
data class RegisterEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "LastName")
    var lastName: String,

    @ColumnInfo(name = "FirstName")
    var firstName: String,

    @ColumnInfo(name = "Login")
    var login: String,

    @ColumnInfo(name = "Email")
    var email: String,

    @ColumnInfo(name = "Password")
    var password: String,

    @ColumnInfo(name = "Photo", defaultValue = "res/mipmap-hdpi/example_icon.png")
    var photo: String?
)
