package com.ckane.colorflash.presentation.impl

import android.util.Log
import com.ckane.colorflash.cache.entity.UserInfo
import com.ckane.colorflash.model.PowerUpModel
import com.ckane.colorflash.presentation.StorePresenter
import com.ckane.colorflash.repository.LocalStorage
import com.ckane.colorflash.repository.UserInfoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StorePresenterImpl(val repository: LocalStorage,
                         private val userInfoRepository: UserInfoRepository,
                         val view: com.ckane.colorflash.android.PowerUpView,
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
            Log.v("[Money]", uInfo.money.toString())
            Log.v("[UserName]", uInfo.userName)
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