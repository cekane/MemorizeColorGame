package com.ckane.colorflash.cache.entity


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "high_score")
class HighScore(var name: String,
                var score: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}