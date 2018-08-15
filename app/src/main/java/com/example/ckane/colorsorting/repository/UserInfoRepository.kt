package com.example.ckane.colorsorting.repository

import com.example.ckane.colorsorting.cache.entity.UserInfo
import io.reactivex.Completable
import io.reactivex.Single

interface UserInfoRepository {
    fun getUserInfo(userName: String): Single<UserInfo>
    fun insertNewUser(userInfo: UserInfo): Completable
    fun updateUserCoins(userName: String, coins: Int): Completable
    fun updatePowerUp(powerUp : String, userName: String): Completable
}