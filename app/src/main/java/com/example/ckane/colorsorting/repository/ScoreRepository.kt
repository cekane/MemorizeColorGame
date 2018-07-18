package com.example.ckane.colorsorting.repository

import com.example.ckane.colorsorting.cache.entity.HighScore
import io.reactivex.Completable
import io.reactivex.Single

interface ScoreRepository {
    fun getAllHighScores(): Single<List<HighScore>>
    fun insertScore(newHighScore: HighScore): Completable
    fun getHighScore(): Single<HighScore>
}