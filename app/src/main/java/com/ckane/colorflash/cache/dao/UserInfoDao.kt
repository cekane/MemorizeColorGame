package com.ckane.colorflash.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.ckane.colorflash.cache.entity.UserInfo

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_info WHERE userName = :userName")
    fun getAllUserInfo(userName: String): UserInfo

    @Insert
    fun insertNewUser(userInfo: UserInfo)

    @Query("UPDATE user_info SET money = money + :coins WHERE userName = :userName")
    fun updateCoins(userName: String, coins: Int)

    @Query("UPDATE user_info set powerUpA = powerUpA + :num WHERE userName = :userName")
    fun updatePowerUpA(userName: String, num : Int)

    @Query("UPDATE user_info set powerUpB = powerUpB + :num WHERE userName = :userName")
    fun updatePowerUpB(userName: String, num : Int)

    @Query("UPDATE user_info set powerUpC = powerUpC + :num WHERE userName = :userName")
    fun updatePowerUpC(userName: String, num : Int)

    @Query("UPDATE user_info set powerUpD = powerUpD + :num WHERE userName = :userName")
    fun updatePowerUpD(userName: String, num : Int)
}