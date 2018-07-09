package com.example.ckane.colorsorting.repository.impl

import com.example.ckane.colorsorting.cache.AppDatabase
import com.example.ckane.colorsorting.cache.entity.HighScore
import com.example.ckane.colorsorting.repository.ScoreRepository
import io.reactivex.Completable
import io.reactivex.Single

class ScoreRepositoryImpl(db: AppDatabase) : ScoreRepository {

    private val dao = db.getHighScoreDao()

    override fun getAllHighScores(): Single<List<HighScore>> = Single.create {
        it.onSuccess(dao.getAllHighScores())
    }

    override fun insertScore(newHighScore: HighScore): Completable = Completable.create {
        dao.insertScore(newHighScore)
        it.onComplete()
    }
}