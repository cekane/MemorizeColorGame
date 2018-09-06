package com.ckane.colorflash.presentation


interface MainMenuPresenter {
    fun getLocalUserName(): String
    fun setLocalUsername(userName: String)
    fun handleRegistration(userName: String)
    fun setUpAnimatedView()
    fun cleanUp()
}