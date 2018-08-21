package com.example.ckane.colorsorting.presentation.impl

import android.os.CountDownTimer
import android.util.Log
import com.example.ckane.colorsorting.android.MenuView
import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.presentation.MainMenuPresenter
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.UserInfoRepository
import com.example.ckane.colorsorting.util.createCardList
import io.reactivex.schedulers.Schedulers

class MainMenuPresenterImpl(private val repository: LocalStorage,
                            private val userInfoRepository: UserInfoRepository,
                            private val view: MenuView) : MainMenuPresenter {

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

class AnimatedTimer(howLong: Long, tick : Long, val view : MenuView) : CountDownTimer(howLong, tick){
    override fun onFinish() {
        this.cancel()
    }

    override fun onTick(millisUntilFinished: Long) {
        view.updateCards(createCardList(false, 16))
    }
}