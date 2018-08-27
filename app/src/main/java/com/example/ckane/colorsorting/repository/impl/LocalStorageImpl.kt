package com.example.ckane.colorsorting.repository.impl

import android.content.SharedPreferences
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

    override fun insertLocalUsername(username: String) {
        with(sharedPref.edit()) {
            putString("SAVED_USER_NAME", username)
            apply()
        }
    }

    override fun isSoundOn(): Boolean = sharedPref.getBoolean("SOUND_STATUS", true)

    override fun toggleSound(soundOn : Boolean) {
        with(sharedPref.edit()) {
            putBoolean("SOUND_STATUS", soundOn)
            apply()
        }
    }

    override fun isHapticOn(): Boolean = sharedPref.getBoolean("HAPTIC_STATUS", true)

    override fun toggleHaptic(hapticOn: Boolean) {
        with(sharedPref.edit()) {
            putBoolean("HAPTIC_STATUS", hapticOn)
            apply()
        }
    }
}