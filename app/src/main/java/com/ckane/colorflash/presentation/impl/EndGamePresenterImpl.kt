package com.ckane.colorflash.presentation.impl

import android.util.Log
import com.ckane.colorflash.presentation.EndGamePresenter
import com.ckane.colorflash.repository.LocalStorage
import com.ckane.colorflash.repository.UserInfoRepository
import io.reactivex.schedulers.Schedulers

class EndGamePresenterImpl(private val repository: LocalStorage, private val userInfoRepository: UserInfoRepository) : EndGamePresenter {
    override fun updateHighScore(mode: String, score: Int) {
        if (score > repository.getLocalHighScore(mode)) {
            repository.insertLocalHighScore(mode, score)
        }
    }

    override fun determineCoins(score: Int, mode: String): Int {
        var marker1 = 0
        var marker2 = 0
        when (mode) {
            "CHALLENGE_MODE" -> {
                if (score > 20) {
                    marker1 = 10
                }
                if (score > 40) {
                    marker2 = 20
                }
                return (score * 3) + marker1 + marker2
            }
            "CLASSIC_MODE_EASY" -> {
                if (score > 40) {
                    marker1 = 10
                }
                if (score > 80) {
                    marker2 = 20
                }
                return (score * 1) + marker1 + marker2
            }
            "CLASSIC_MODE_HARD" -> {
                if (score > 25) {
                    marker1 = 10
                }
                if (score > 50) {
                    marker2 = 20
                }
                return (score * 3) + marker1 + marker2
            }
        }
        return 0
    }

    override fun updateCoins(userName: String, coins: Int) {
        userInfoRepository.updateUserCoins(userName, coins).subscribeOn(Schedulers.io()).subscribe {
            Log.v("[UserInfo]", "Added $coins to $userName")
        }

    }

    override fun getUserName(): String = repository.getLocalUsername()

}