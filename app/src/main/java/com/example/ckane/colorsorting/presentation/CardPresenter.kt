package com.example.ckane.colorsorting.presentation

interface CardPresenter {
    fun startRound()
    fun makeColors(color: String)
    fun createSingleColorList()
    fun updateCard(position: Int)
    fun setGameMode(gameMode : String)
}