package com.example.ckane.colorsorting.repository.impl

import android.content.SharedPreferences
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.repository.LocalStorage

class LocalStorageImpl(private val sharedPref: SharedPreferences) : LocalStorage {

    override fun getLocalHighScore(mode: String): Int = sharedPref.getInt(mode + "_HIGH_SCORE", 0)

    override fun insertLocalHighScore(mode: String, score: Int) {
        with(sharedPref.edit()) {
            putInt(mode + "_HIGH_SCORE", score)
            apply()
        }
    }

    override fun getLocalUsername(): String = sharedPref.getString("SAVED_USER_NAME", "")

    override fun insertLocalUsername(username: String){
        with(sharedPref.edit()) {
            putString("SAVED_USER_NAME",username)
            apply()
        }
    }
}