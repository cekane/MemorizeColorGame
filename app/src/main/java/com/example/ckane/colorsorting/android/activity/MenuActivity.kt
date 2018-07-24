package com.example.ckane.colorsorting.android.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.activity.levels.ChallengeMode
import com.example.ckane.colorsorting.android.activity.levels.ClassicMode
import com.example.ckane.colorsorting.presentation.MainMenuPresenter
import com.example.ckane.colorsorting.presentation.impl.MainMenuPresenterImpl
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.impl.LocalStorageImpl

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        val sharedPref = this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE)
        val repository : LocalStorage = LocalStorageImpl(sharedPref)
        val presenter : MainMenuPresenter = MainMenuPresenterImpl(repository)


        val playerName = findViewById<EditText>(R.id.edit_name_text)
        val userName = presenter.getLocalUserName()
        playerName.setText(userName)

        val classicModeBtn : Button = findViewById(R.id.classic_mode)
        classicModeBtn.setOnClickListener{
            startActivity(Intent(this, ClassicMode::class.java))
        }
        
        val challengeModeBtn: Button = findViewById(R.id.challenge_mode)
        challengeModeBtn.setOnClickListener {
            if (getPlayerName(playerName) == "") {
                Toast.makeText(this, "Enter a name to play", Toast.LENGTH_SHORT).show()
            } else {
                presenter.setLocalUsername(getPlayerName(playerName))
                startActivity(Intent(this, ChallengeMode::class.java))
            }
        }

        val howToButton : Button = findViewById(R.id.how_to_play)
        howToButton.setOnClickListener {
            //Todo placeholder for how to play
        }

        val scoreBoardButton : Button = findViewById(R.id.scoreboard)
        scoreBoardButton.setOnClickListener {
            startActivity(Intent(this, HighScoreActivity::class.java))
        }
    }
    override fun onBackPressed() {
        //Nothing to do here
    }

    private fun getPlayerName(playerName : EditText) : String = playerName.text.toString()
}
