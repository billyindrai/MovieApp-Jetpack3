package com.billyindrai.subjetpack3.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.billyindrai.subjetpack3.data.entity.MovieEntity
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity

@Database(entities = [MovieEntity::class, TvShowsEntity::class], version = 1, exportSchema = false)
abstract  class Database  : RoomDatabase(){
    abstract fun databaseDAO(): DatabaseDAO

    companion object{
        @Volatile
        private var INSTANCE: com.billyindrai.subjetpack3.data.local.Database? = null

        fun getDatabase(context: Context): com.billyindrai.subjetpack3.data.local.Database {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.billyindrai.subjetpack3.data.local.Database::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}