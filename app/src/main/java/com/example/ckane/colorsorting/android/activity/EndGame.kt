package com.example.ckane.colorsorting.android.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.activity.levels.LevelOne
import com.example.ckane.colorsorting.cache.AppDatabase
import com.example.ckane.colorsorting.presentation.EndGamePresenter
import com.example.ckane.colorsorting.presentation.impl.EndGamePresenterImpl
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.impl.LocalStorageImpl


class EndGame : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        val score = intent.getIntExtra("FINAL_SCORE", 0)
        val sharedPref = this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE)
        val repository : LocalStorage = LocalStorageImpl(sharedPref)
        val presenter : EndGamePresenter = EndGamePresenterImpl(repository)

        presenter.updateHighScore(score)
        presenter.updateHighScoreDatabase(score, AppDatabase.getInstance(this))

        val scoreTextView = findViewById<TextView>(R.id.score)
        scoreTextView.text = score.toString()

        val tryAgainBtn: Button = findViewById(R.id.try_again)
        tryAgainBtn.setOnClickListener {
            startActivity(Intent(this, LevelOne::class.java))
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
