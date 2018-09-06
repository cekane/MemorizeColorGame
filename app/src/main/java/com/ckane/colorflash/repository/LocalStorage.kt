package com.ckane.colorflash.repository

interface LocalStorage {
    fun getLocalHighScore(mode: String): Int
    fun insertLocalHighScore(mode: String, score: Int)
    fun getLocalUsername(): String
    fun insertLocalUsername(username: String)
    fun isSoundOn() : Boolean
    fun toggleSound(soundOn : Boolean)
    fun isHapticOn() : Boolean
    fun toggleHaptic(hapticOn: Boolean)
}