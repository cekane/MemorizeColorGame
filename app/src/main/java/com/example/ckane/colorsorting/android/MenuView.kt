package com.example.ckane.colorsorting.android

import com.example.ckane.colorsorting.cache.entity.UserInfo

interface MenuView{
    fun updateUserInfoUi(userInfo : UserInfo)
}