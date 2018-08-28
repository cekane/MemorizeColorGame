package com.example.ckane.colorsorting.android

import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.model.Card

interface CardView {
    fun newCard(newCard: Card)
    fun newData(newCards: MutableList<Card>,  clickable: Boolean)
    fun setColorText(colorText: String, textSelector: Int)
    fun setCounterText(counterText: String)
    fun getCounterNumber(): Int
    fun timer(time: Long, f: () -> Unit)
    fun endGame(score: Int)
    fun roundEndFragment()
    fun setColorTextColor(textColor: String)
    fun newAdapter(cardList : MutableList<Card>, rowCount: Int, clickable: Boolean, itemLayout: Int)
    fun updateLocalHighScore(highScore: Int)
    fun enablePowerUps(userInfo : UserInfo)
}