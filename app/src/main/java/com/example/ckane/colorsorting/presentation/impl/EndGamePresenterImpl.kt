package com.example.ckane.colorsorting.presentation.impl

import android.util.Log
import com.example.ckane.colorsorting.cache.AppDatabase
import com.example.ckane.colorsorting.cache.entity.HighScore
import com.example.ckane.colorsorting.presentation.EndGamePresenter
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.ScoreRepository
import com.example.ckane.colorsorting.repository.impl.ScoreRepositoryImpl
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class EndGamePresenterImpl(private val repository: LocalStorage) : EndGamePresenter {
    private val scheduler: Scheduler = Schedulers.io()

    override fun updateHighScore(score: Int) {
        if (score > repository.getLocalHighScore()) {
            repository.insertLocalHighScore(score)
        }
    }

    override fun updateHighScoreDatabase(score: Int, appDatabase: AppDatabase) {
        val userName = repository.getLocalUsername()
        Single.just(appDatabase)
                .subscribeOn(scheduler)
                .subscribe { db: AppDatabase ->
                    val scoreRepository: ScoreRepository = ScoreRepositoryImpl(db)
                    val scoreToInsert = HighScore(userName, score)
                    scoreRepository.insertScore(scoreToInsert)
                            .subscribeOn(scheduler)
                            .subscribe({
                                Log.v("[Room Insert]", "Inserted ${scoreToInsert.score} to db")
                            }, {
                                Log.v("[Room Error]", "Error Inserted ${scoreToInsert.score} to db", it)
                            })
                }
    }
}