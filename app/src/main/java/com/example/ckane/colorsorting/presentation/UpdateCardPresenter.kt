package com.example.ckane.colorsorting.presentation

interface UpdateCardPresenter {
    fun updateCard(position: Int)
    fun isSoundOn() : Boolean
    fun isHapticOn() : Boolean
}