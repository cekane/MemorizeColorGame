package com.ckane.colorflash.presentation.impl

import android.os.CountDownTimer
import android.util.Log
import com.ckane.colorflash.cache.entity.UserInfo
import com.ckane.colorflash.presentation.MainMenuPresenter
import com.ckane.colorflash.repository.LocalStorage
import com.ckane.colorflash.repository.UserInfoRepository
import com.ckane.colorflash.util.createCardList
import io.reactivex.schedulers.Schedulers

class MainMenuPresenterImpl(private val repository: LocalStorage,
                            private val userInfoRepository: UserInfoRepository,
                            private val view: com.ckane.colorflash.android.MenuView) : MainMenuPresenter {

    val myTimer : AnimatedTimer by lazy {
        AnimatedTimer(1000000, 1000, view)
    }
    override fun getLocalUserName(): String = repository.getLocalUsername()

    override fun setLocalUsername(userName: String) {
        repository.insertLocalUsername(userName)
    }

    override fun handleRegistration(userName: String) {
        if (getLocalUserName().isEmpty()) {
            setLocalUsername(userName)
            //Insert new user into database
            createNewUser(UserInfo(userName))
        }
        Log.v("[UserInfo]Local user name", getLocalUserName())
    }

    private fun createNewUser(userInfo: UserInfo) {
        userInfoRepository.insertNewUser(userInfo).subscribeOn(Schedulers.io())
                .subscribe {
                    Log.v("[UserInfo]", "Successfully inserted user into table")
                }
    }

    override fun setUpAnimatedView() {
        myTimer.start()
    }

    override fun cleanUp() {
        myTimer.onFinish()
    }
}

class AnimatedTimer(howLong: Long, tick : Long, val view : com.ckane.colorflash.android.MenuView) : CountDownTimer(howLong, tick){
    override fun onFinish() {
        this.cancel()
    }

    override fun onTick(millisUntilFinished: Long) {
        view.updateCards(createCardList(false, 16))
    }
}