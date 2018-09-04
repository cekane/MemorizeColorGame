package com.example.ckane.colorsorting.presentation.impl

import android.util.Log
import com.example.ckane.colorsorting.android.PowerUpView
import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.model.PowerUpModel
import com.example.ckane.colorsorting.presentation.StorePresenter
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.UserInfoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StorePresenterImpl(val repository: LocalStorage,
                         private val userInfoRepository: UserInfoRepository,
                         val view: PowerUpView,
                         val powerUpList: MutableList<PowerUpModel>) : StorePresenter {

    val userName: String by lazy {
        repository.getLocalUsername()
    }

    var uInfo = UserInfo("")
    override fun coinTransaction(type: String, ammount : Int, powerUp: Int){
        when (type){
            "BUY" -> {
                if(powerUp != -1 && uInfo.money >= powerUpList[powerUp].cost){
                    updateCoinInfo((powerUpList[powerUp].cost)*-1)
                    buyPowerUp(powerUp)
                }
            }
            "ADD" -> {
                updateCoinInfo(ammount)
            }
        }
    }

    override fun updateUserInfo() {
        userInfoRepository.getUserInfo(userName).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe({
            uInfo = it
            view.setUserInfo(uInfo)
        }, {
            view.setUserInfo(UserInfo(""))
            Log.v("[Power Up User Info]", it.message)
        })
    }

    override fun buyPowerUp(powerUp: Int) {
        userInfoRepository.updatePowerUp(powerUp, userName).subscribeOn(Schedulers.io()).subscribe({
            updateUserInfo()
            Log.v("[Buying Power Up User Info]", "Success")
        }, {
            Log.v("[Buying Power Up User Info]", it.message)
        })
    }

    private fun updateCoinInfo(newCoins : Int){
        userInfoRepository.updateUserCoins(userName, newCoins).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe{
            updateUserInfo()
            Log.v("[Updated Coins]", "$newCoins")
        }
    }
}