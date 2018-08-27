package com.example.ckane.colorsorting.android

import com.example.ckane.colorsorting.model.Card

interface CardListManager {
    fun newCard(newCard: Card)
    fun newData(newCards: MutableList<Card>, clickable: Boolean)
}