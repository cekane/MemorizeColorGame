package com.ckane.colorflash.repository

import com.ckane.colorflash.cache.entity.UserInfo
import io.reactivex.Completable
import io.reactivex.Single

interface UserInfoRepository {
    fun getUserInfo(userName: String): Single<UserInfo>
    fun insertNewUser(userInfo: UserInfo): Completable
    fun updateUserCoins(userName: String, coins: Int): Completable
    fun updatePowerUp(powerUp: Int, userName: String, num : Int = 1): Completable
}