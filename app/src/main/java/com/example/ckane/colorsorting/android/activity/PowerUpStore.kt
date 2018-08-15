package com.example.ckane.colorsorting.android.activity

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.PowerUpView
import com.example.ckane.colorsorting.cache.AppDatabase
import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.presentation.StorePresenter
import com.example.ckane.colorsorting.presentation.impl.StorePresenterImpl
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.UserInfoRepository
import com.example.ckane.colorsorting.repository.impl.LocalStorageImpl
import com.example.ckane.colorsorting.repository.impl.UserInfoRepositoryImpl

class PowerUpStore : AppCompatActivity(), PowerUpView {
    private val powerUp1: Button by lazy { findViewById<Button>(R.id.power_up_1) }
    private val powerUp1Text1: TextView by lazy { findViewById<TextView>(R.id.power_up_text_1) }
    private val powerUp2: Button by lazy { findViewById<Button>(R.id.power_up_2) }
    private val powerUp1Text2: TextView by lazy { findViewById<TextView>(R.id.power_up_text_2) }
    private val powerUp3: Button by lazy { findViewById<Button>(R.id.power_up_3) }
    private val powerUp1Text3: TextView by lazy { findViewById<TextView>(R.id.power_up_text_3) }
    private val powerUp4: Button by lazy { findViewById<Button>(R.id.power_up_4) }
    private val powerUp1Text4: TextView by lazy { findViewById<TextView>(R.id.power_up_text_4) }
    private val userNameTextView: TextView by lazy { findViewById<TextView>(R.id.user_name_power_up_store) }
    private val moneyTextView: TextView by lazy { findViewById<TextView>(R.id.money_power_up_store) }

    private val sharedPref: SharedPreferences by lazy {
        this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE)
    }
    val repository: LocalStorage by lazy {
        LocalStorageImpl(sharedPref)
    }
    val db: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }
    private val userInfoRepository: UserInfoRepository by lazy {
        UserInfoRepositoryImpl(db)
    }
    val presenter: StorePresenter by lazy { StorePresenterImpl(repository, userInfoRepository, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_power_up_store)
        presenter.updateUserInfo()
        powerUp1.setOnClickListener {
            presenter.buyPowerUp("A")
        }

        powerUp2.setOnClickListener {
            presenter.buyPowerUp("B")
        }

        powerUp3.setOnClickListener {
            presenter.buyPowerUp("C")
        }

        powerUp4.setOnClickListener {
            presenter.buyPowerUp("D")
        }
    }

    override fun setUserInfo(userInfo: UserInfo) {
        userNameTextView.text = userInfo.userName
        moneyTextView.text = resources.getString(R.string.coins, userInfo.money.toString())
        powerUp1Text1.text = resources.getString(R.string.power_up_1_status, userInfo.powerUpA.toString())
        powerUp1Text2.text = resources.getString(R.string.power_up_2_status, userInfo.powerUpB.toString())
        powerUp1Text3.text = resources.getString(R.string.power_up_3_status, userInfo.powerUpC.toString())
        powerUp1Text4.text = resources.getString(R.string.power_up_4_status, userInfo.powerUpD.toString())
    }
}
