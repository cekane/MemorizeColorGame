package com.example.ckane.colorsorting.repository.impl

import com.example.ckane.colorsorting.cache.AppDatabase
import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.repository.UserInfoRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserInfoRepositoryImpl(db: AppDatabase) : UserInfoRepository {

    private val dao = db.getUserInfoDao()

    override fun getUserInfo(userName : String) : Single<UserInfo> = Single.create{
        it.onSuccess(dao.getAllUserInfo(userName))
    }

    override fun insertNewUser(userInfo: UserInfo) : Completable = Completable.create {
        dao.insertNewUser(userInfo)
        it.onComplete()
    }

    override fun updateUserCoins(userName: String, coins: Int) : Completable = Completable.create {
        dao.updateCoins(userName, coins)
        it.onComplete()
    }
}