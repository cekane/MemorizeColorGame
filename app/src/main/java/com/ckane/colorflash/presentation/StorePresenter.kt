package com.ckane.colorflash.presentation

interface StorePresenter {
    fun updateUserInfo()
    fun buyPowerUp(powerUp: Int)
    fun coinTransaction(type: String, ammount : Int = 0, powerUp: Int = -1)
}