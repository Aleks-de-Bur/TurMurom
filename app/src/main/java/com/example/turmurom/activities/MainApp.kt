package com.example.turmurom.activities

import android.app.Application
import com.example.turmurom.database.MainDb

class MainApp : Application() {
    val database by lazy {MainDb.getDB(this)}
}