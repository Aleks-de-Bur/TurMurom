package com.example.turmurom.database

import android.content.Context
import androidx.room.*
import com.example.turmurom.database.dao.*
import com.example.turmurom.database.models.*

//При миграциях БД обновлять версию!
@Database(
    entities = [Mark::class,
        Guide::class, Excursion::class,
        ExcursionPhoto::class, MarkPhoto::class,
        Route::class, RouteMarks::class,
        Schedule::class, RegisterEntity::class,
        UserVisited::class, UserElected::class,
        Category::class], version = 1
)
abstract class MainDb : RoomDatabase() {
    abstract fun getExcursionDao(): ExcursionDao
    abstract fun getExcursionPhotoDao(): ExcursionPhotoDao
    abstract fun getRouteDao(): RouteDao
    abstract fun getMarkDao(): MarkDao
    abstract fun getMarkPhotoDao(): MarkPhotoDao
    abstract fun getGuideDao(): GuideDao
    abstract fun getScheduleDao(): ScheduleDao
    abstract fun getRegisterEntityDao(): RegisterEntityDao

    companion object {
        @Volatile
        private var INSTANCE: MainDb? = null
        fun getDB(context: Context): MainDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDb::class.java,
                    "turMur.db" //Название базы данных
                ).build()
                instance
            }
        }
    }
}