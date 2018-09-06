package com.ckane.colorflash.repository

import com.ckane.colorflash.cache.entity.HighScore
import io.reactivex.Completable
import io.reactivex.Single

interface ScoreRepository {
    fun getAllHighScores(): Single<List<HighScore>>
    fun insertScore(newHighScore: HighScore): Completable
    fun getHighScore(): Single<HighScore>
}