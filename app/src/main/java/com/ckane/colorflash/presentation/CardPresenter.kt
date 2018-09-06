package com.ckane.colorflash.presentation

interface CardPresenter : UpdateCardPresenter {
    fun startRound()
    fun makeColors(color: String)
    fun createSingleColorList()
    fun setGameMode(gameMode: String)
    fun setGameTime()
    fun activateShield()
    fun replayBoard()
    fun showOneColor()
    fun showTargetedColor()
    fun getUserInfo()
    fun updatePowerUpAmount(powerUp : Int)
}