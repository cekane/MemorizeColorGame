package com.ckane.colorflash.repository.impl

import com.ckane.colorflash.cache.AppDatabase
import com.ckane.colorflash.cache.entity.HighScore
import com.ckane.colorflash.repository.ScoreRepository
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

    override fun getHighScore(): Single<HighScore> = Single.create {
        it.onSuccess(dao.getHighScore())
    }
}