package com.example.ckane.colorsorting.presentation.impl

import android.util.Log
import com.example.ckane.colorsorting.android.PowerUpView
import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.presentation.StorePresenter
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.UserInfoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StorePresenterImpl(val repository: LocalStorage,
                         private val userInfoRepository: UserInfoRepository,
                         val view: PowerUpView) : StorePresenter {

    val userName: String by lazy {
        repository.getLocalUsername()
    }

    override fun updateUserInfo() {
        userInfoRepository.getUserInfo(userName).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe({
            view.setUserInfo(it)
        }, {
            view.setUserInfo(UserInfo(""))
            Log.v("[Power Up User Info]", it.message)
        })
    }

    override fun buyPowerUp(powerUp: String) {
        userInfoRepository.updatePowerUp(powerUp, userName).subscribeOn(Schedulers.io()).subscribe({
            updateUserInfo()
        }, {
            Log.v("[Buying Power Up User Info]", it.message)
        })
    }
}