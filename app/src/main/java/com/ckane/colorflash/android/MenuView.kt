package com.ckane.colorflash.android

import com.ckane.colorflash.model.Card

interface MenuView {
    fun updateCards(cards: MutableList<Card>)
}