package com.example.ckane.colorsorting.repository

interface LocalStorage {
    fun getLocalHighScore() : Int
    fun insertLocalHighScore(score: Int)
    fun getLocalUsername(): String
    fun insertLocalUsername(username: String)
}