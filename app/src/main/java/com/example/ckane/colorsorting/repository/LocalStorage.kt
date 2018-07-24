package com.example.ckane.colorsorting.repository

interface LocalStorage {
    fun getLocalHighScore(mode : String) : Int
    fun insertLocalHighScore(mode : String, score: Int)
    fun getLocalUsername(): String
    fun insertLocalUsername(username: String)
}