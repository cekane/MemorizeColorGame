package com.example.ckane.colorsorting.android

import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.model.Card

interface MenuView{
    fun updateCards(cards : MutableList<Card>)
}