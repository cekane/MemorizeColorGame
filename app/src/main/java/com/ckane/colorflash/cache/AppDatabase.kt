package com.ckane.colorflash.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ckane.colorflash.cache.dao.HighScoreDao
import com.ckane.colorflash.cache.dao.UserInfoDao
import com.ckane.colorflash.cache.entity.HighScore
import com.ckane.colorflash.cache.entity.UserInfo

@Database(entities = [(HighScore::class), (UserInfo::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHighScoreDao(): HighScoreDao
    abstract fun getUserInfoDao(): UserInfoDao

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