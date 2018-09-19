package com.ckane.colorflash.presentation

interface UpdateCardPresenter {
    fun updateCard(position: Int, sound: Boolean)
    fun isSoundOn() : Boolean
    fun isHapticOn() : Boolean
}