package com.example.ckane.colorsorting.cache.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user_info")
class UserInfo(var userName: String,
               var money: Int = 0,
               var powerUpA: Int = 0,
               var powerUpB: Int = 0,
               var powerUpC: Int = 0,
               var powerUpD: Int = 0) {
    @PrimaryKey(autoGenerate = true)
    var userId: Int? = null
}