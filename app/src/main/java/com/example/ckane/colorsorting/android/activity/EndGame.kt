package com.example.ckane.colorsorting.android.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.cache.AppDatabase
import com.example.ckane.colorsorting.cache.entity.HighScore
import com.example.ckane.colorsorting.repository.ScoreRepository
import com.example.ckane.colorsorting.repository.impl.ScoreRepositoryImpl
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class EndGame : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        val score = intent.getIntExtra("FINAL_SCORE", 0)
        val sharedPref = this.getSharedPreferences( "Data_file" ,android.content.Context.MODE_PRIVATE )
        val userName = sharedPref.getString(getString(R.string.saved_user_name), "")
        Log.v("[Shared Pref]", "Shared pref read : $userName")
        val scheduler = Schedulers.io()
        Single.just(AppDatabase.getInstance(this))
                .subscribeOn(scheduler)
                .subscribe { db: AppDatabase ->
                    val scoreRepository: ScoreRepository = ScoreRepositoryImpl(db)
                    val scoreToInsert = HighScore(userName, score)
                    scoreRepository.insertScore(scoreToInsert)
                            .subscribeOn(scheduler)
                            .subscribe({
                                Log.v("[Room Insert]", "Inserted ${scoreToInsert.score} to db")
                            },{
                                Log.v("[Room Error]", "Error Inserted ${scoreToInsert.score} to db", it)
                            })
                }

        val scoreTextView = findViewById<TextView>(R.id.score)
        scoreTextView.text = score.toString()

        val tryAgainBtn: Button = findViewById(R.id.try_again)
        tryAgainBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val menuButton: Button = findViewById(R.id.menu)
        menuButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MenuActivity::class.java))
    }
}
