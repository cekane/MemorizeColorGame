package com.ckane.colorflash.android.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.ckane.colorflash.R
import com.ckane.colorflash.android.adapter.ScoreRecyclerAdapter
import com.ckane.colorflash.cache.AppDatabase
import com.ckane.colorflash.repository.ScoreRepository
import com.ckane.colorflash.repository.impl.ScoreRepositoryImpl
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class HighScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.high_score_activity)

        val rView: RecyclerView = findViewById(R.id.score_adapter)
        val layoutManager = LinearLayoutManager(this)
        rView.layoutManager = layoutManager

        val scheduler = Schedulers.io()
        Single.just(AppDatabase.getInstance(this))
                .subscribeOn(scheduler)
                .subscribe { db: AppDatabase ->
                    val scoreRepository: ScoreRepository = ScoreRepositoryImpl(db)

                    scoreRepository.getAllHighScores().subscribeOn(scheduler).subscribe({
                        val scoreAdapter = ScoreRecyclerAdapter(this, it)
                        rView.adapter = scoreAdapter

                        it.map { highScore ->
                            Log.v("[Entries]", "Item : ${highScore.score} ${highScore.name}")
                        }
                    }, {
                        Log.v("[Entries]", "Failed", it)
                    })
                }
    }
}
