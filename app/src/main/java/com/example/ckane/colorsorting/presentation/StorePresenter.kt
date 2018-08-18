package com.example.ckane.colorsorting.presentation

import com.example.ckane.colorsorting.cache.entity.UserInfo

interface StorePresenter {
    fun updateUserInfo()
    fun buyPowerUp(powerUp : Int)
}