package com.example.ckane.colorsorting.presentation

import com.example.ckane.colorsorting.repository.LocalStorage

interface CardPresenter {
    fun startRound()
    fun makeColors(color: String)
    fun createSingleColorList()
    fun updateCard(position: Int)
    fun setGameMode(gameMode : String)
    fun setRepository(repository : LocalStorage)
}