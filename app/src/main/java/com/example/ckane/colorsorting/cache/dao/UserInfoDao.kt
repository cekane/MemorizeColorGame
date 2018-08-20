package com.example.ckane.colorsorting.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.ckane.colorsorting.cache.entity.UserInfo

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_info WHERE userName = :userName")
    fun getAllUserInfo(userName: String): UserInfo

    @Insert
    fun insertNewUser(userInfo: UserInfo)

    @Query("UPDATE user_info SET money = money + :coins WHERE userName = :userName")
    fun updateCoins(userName: String, coins: Int)

    @Query("UPDATE user_info set powerUpA = powerUpA + 1 WHERE userName = :userName")
    fun updatePowerUpA(userName: String)

    @Query("UPDATE user_info set powerUpB = powerUpB + 1 WHERE userName = :userName")
    fun updatePowerUpB(userName: String)

    @Query("UPDATE user_info set powerUpC = powerUpC + 1 WHERE userName = :userName")
    fun updatePowerUpC(userName: String)

    @Query("UPDATE user_info set powerUpD = powerUpD + 1 WHERE userName = :userName")
    fun updatePowerUpD(userName: String)
}