package com.ckane.colorflash.presentation

interface UpdateCardPresenter {
    fun updateCard(position: Int)
    fun isSoundOn() : Boolean
    fun isHapticOn() : Boolean
}