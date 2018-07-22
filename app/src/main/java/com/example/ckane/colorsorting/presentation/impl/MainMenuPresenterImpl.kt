package com.example.ckane.colorsorting.presentation.impl

import com.example.ckane.colorsorting.presentation.MainMenuPresenter
import com.example.ckane.colorsorting.repository.LocalStorage

class MainMenuPresenterImpl(private val repository : LocalStorage) : MainMenuPresenter {

    override fun getLocalUserName() : String = repository.getLocalUsername()

    override fun setLocalUsername(userName: String){
        repository.insertLocalUsername(userName)
    }
}