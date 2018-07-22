package com.example.ckane.colorsorting.presentation

import com.example.ckane.colorsorting.cache.AppDatabase

interface EndGamePresenter {
    fun updateHighScore(score : Int)
    fun updateHighScoreDatabase(score: Int, appDatabase : AppDatabase)
}