package com.example.ckane.colorsorting.android.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import com.example.ckane.colorsorting.R

class EndGame : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        val score = intent.getIntExtra("FINAL_SCORE", 0)
        val scoreTextView = findViewById<TextView>(R.id.score)
        scoreTextView.text = score.toString()

        val tryAgainBtn : Button = findViewById(R.id.try_again)
        tryAgainBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val menuButton : Button = findViewById(R.id.menu)
        menuButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }
}
