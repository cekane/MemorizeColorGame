package com.example.ckane.colorsorting.presentation.impl

import android.util.Log
import com.example.ckane.colorsorting.android.MenuView
import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.presentation.MainMenuPresenter
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.UserInfoRepository
import io.reactivex.schedulers.Schedulers

class MainMenuPresenterImpl(private val repository: LocalStorage,
                            private val userInfoRepository: UserInfoRepository,
                            private val view: MenuView) : MainMenuPresenter {

    override fun getLocalUserName(): String = repository.getLocalUsername()

    override fun setLocalUsername(userName: String) {
        repository.insertLocalUsername(userName)
    }

    override fun handleRegistration(userName: String) {
        if (getLocalUserName().isEmpty()) {
            setLocalUsername(userName)
            //Insert new user into database
            createNewUser(UserInfo(userName))
        } else {
            //Get the current user from the database
            getUserInfo(userName)
        }
        Log.v("[UserInfo]Local user name", getLocalUserName())
    }

    private fun getUserInfo(userName: String) {
        userInfoRepository.getUserInfo(userName).subscribeOn(Schedulers.io())
                .subscribe({
                    Log.v("[UserInfo]", it.toString())
                    view.updateUserInfoUi(it)
                }, {
                    Log.v("[UserInfo]", "Error", it)
                })
    }

    private fun createNewUser(userInfo: UserInfo) {
        userInfoRepository.insertNewUser(userInfo).subscribeOn(Schedulers.io())
                .subscribe({
                    Log.v("[UserInfo]", "Successfully inserted user into table")
                })
    }
}