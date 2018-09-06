package com.ckane.colorflash.repository.impl

import com.ckane.colorflash.cache.AppDatabase
import com.ckane.colorflash.cache.entity.UserInfo
import com.ckane.colorflash.repository.UserInfoRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserInfoRepositoryImpl(db: AppDatabase) : UserInfoRepository {
    private val dao = db.getUserInfoDao()

    override fun getUserInfo(userName: String): Single<UserInfo> = Single.create {
        it.onSuccess(dao.getAllUserInfo(userName))
    }

    override fun insertNewUser(userInfo: UserInfo): Completable = Completable.create {
        dao.insertNewUser(userInfo)
        it.onComplete()
    }

    override fun updateUserCoins(userName: String, coins: Int): Completable = Completable.create {
        dao.updateCoins(userName, coins)
        it.onComplete()
    }

    override fun updatePowerUp(powerUp: Int, userName: String, num : Int): Completable = Completable.create {
        when (powerUp) {
            0 -> {
                dao.updatePowerUpA(userName, num)
            }
            1 -> {
                dao.updatePowerUpB(userName, num)
            }
            2 -> {
                dao.updatePowerUpC(userName, num)
            }
            3 -> {
                dao.updatePowerUpD(userName, num)
            }
        }
        it.onComplete()
    }
}