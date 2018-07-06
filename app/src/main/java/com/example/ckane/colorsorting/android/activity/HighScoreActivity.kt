package com.example.ckane.colorsorting.android.activity

import android.app.ListActivity
import android.os.Bundle
import android.util.Log
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.cache.AppDatabase
import com.example.ckane.colorsorting.repository.ScoreRepository
import com.example.ckane.colorsorting.repository.impl.ScoreRepositoryImpl
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class HighScoreActivity : ListActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)


        val scheduler = Schedulers.io()
        Single.just(AppDatabase.getInstance(this))
                .subscribeOn(scheduler)
                .subscribe { db: AppDatabase ->
                    val scoreRepository: ScoreRepository = ScoreRepositoryImpl(db)

                    scoreRepository.getAllHighScores().subscribeOn(scheduler).subscribe({
                        it.map{
                            Log.v("[Entries]", "Item : ${it.score} ${it.name}")
                        }
                    },{
                        Log.v("[Entries]", "Failed", it)
                    })
                }

    }
}
