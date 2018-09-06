package com.ckane.colorflash.android.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.ckane.colorflash.R
import com.ckane.colorflash.android.activity.levels.ChallengeMode

class DifficultyPickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty_picker)

        val easyButton: Button = findViewById(R.id.easy_difficulty)
        easyButton.setOnClickListener {
            startActivity(Intent(this, ChallengeMode::class.java).apply {
                putExtra("GAME_MODE", getString(R.string.difficulty_easy))
            })
        }

        val hardButton: Button = findViewById(R.id.hard_difficulty)
        hardButton.setOnClickListener {
            startActivity(Intent(this, ChallengeMode::class.java).apply {
                putExtra("GAME_MODE", getString(R.string.difficulty_hard))
            })
        }

        val challengeModeBtn: Button = findViewById(R.id.challenge_mode)
        challengeModeBtn.setOnClickListener {
            startActivity(Intent(this, ChallengeMode::class.java).apply {
                putExtra("GAME_MODE", getString(R.string.difficulty_challenge))
            })
        }
    }
}
