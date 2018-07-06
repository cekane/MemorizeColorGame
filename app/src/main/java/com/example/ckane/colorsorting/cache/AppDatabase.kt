package com.example.ckane.colorsorting.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.ckane.colorsorting.cache.dao.HighScoreDao
import com.example.ckane.colorsorting.cache.entity.HighScore

@Database(entities = [(HighScore::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHighScoreDao(): HighScoreDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java,
                                "colorFlash.db")
                                .build()
                    }
                }
            }
            return instance ?: getInstance(context)
        }
    }
}