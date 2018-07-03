package com.example.ckane.colorsorting.presentation

interface CardPresenter {
    fun startRound()
    fun updateCard(position: Int)
    fun makeColors(color: String)
    fun createSingleColorList()
}