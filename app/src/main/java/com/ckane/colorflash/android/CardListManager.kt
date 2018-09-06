package com.ckane.colorflash.android

import com.ckane.colorflash.model.Card

interface CardListManager {
    fun newCard(newCard: Card)
    fun newData(newCards: MutableList<Card>)
}