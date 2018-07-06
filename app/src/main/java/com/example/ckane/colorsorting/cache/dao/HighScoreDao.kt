package com.example.ckane.colorsorting.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.ckane.colorsorting.cache.entity.HighScore

@Dao
interface HighScoreDao {
    @Query("SELECT * FROM high_score")
    fun getAllHighScores(): List<HighScore>

    @Insert
    fun insertScore(score: HighScore)
}