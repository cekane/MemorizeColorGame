package com.example.ckane.colorsorting.android.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.ckane.colorsorting.R

import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val startButton : Button = findViewById(R.id.begin_game)
        startButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
