package com.example.ckane.colorsorting

interface CardListManager {
    fun updateCard(position: Int)
    fun makeColors(color : String)
    fun makeGrey()
}