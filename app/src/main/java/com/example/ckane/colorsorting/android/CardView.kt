package com.example.ckane.colorsorting.android

import com.example.ckane.colorsorting.model.Card

interface CardView {
    fun newCard(newCard: Card)
    fun newData(newCards: MutableList<Card>)
    fun setColorText(colorText: String, textSelector: Int)
    fun setCounterText(counterText: String)
    fun getCounterNumber(): Int
    fun timer(time: Long, f: () -> Unit)
    fun endGame(score: Int)
    fun roundEndFragment()
    fun setColorTextColor(textColor: String)
    fun expandGrid(deckSize : Int, rowCount : Int)
    fun updateLocalHighScore(highScore: Int)
}