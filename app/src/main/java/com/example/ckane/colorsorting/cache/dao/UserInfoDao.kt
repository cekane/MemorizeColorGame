package com.example.ckane.colorsorting.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.ckane.colorsorting.cache.entity.UserInfo

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_info WHERE userName = :userName")
    fun getAllUserInfo(userName : String): UserInfo

    @Insert
    fun insertNewUser(userInfo : UserInfo)
}