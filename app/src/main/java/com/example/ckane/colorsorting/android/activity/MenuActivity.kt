package com.example.ckane.colorsorting.android.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ckane.colorsorting.R

import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val playerName = findViewById<EditText>(R.id.edit_name_text)

        val startButton : Button = findViewById(R.id.begin_game)
        startButton.setOnClickListener {
            if(playerName.text.toString() == ""){
                Toast.makeText(this, "Enter a name to play", Toast.LENGTH_SHORT).show()
            }
            else{
                val sharedPref = this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE)
                with(sharedPref.edit()){
                    putString(getString(R.string.saved_user_name), playerName.text.toString())
                    apply()
                }
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        val scoreBoardButton : Button = findViewById(R.id.scoreboard)
        scoreBoardButton.setOnClickListener {
            startActivity(Intent(this, HighScoreActivity::class.java))
        }
    }

}
