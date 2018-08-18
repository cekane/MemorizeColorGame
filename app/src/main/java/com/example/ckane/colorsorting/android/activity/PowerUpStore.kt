package com.example.ckane.colorsorting.android.activity

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.PowerUpView
import com.example.ckane.colorsorting.android.adapter.PowerUpRecyclerAdapter
import com.example.ckane.colorsorting.cache.AppDatabase
import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.model.PowerUpModel
import com.example.ckane.colorsorting.presentation.StorePresenter
import com.example.ckane.colorsorting.presentation.impl.StorePresenterImpl
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.UserInfoRepository
import com.example.ckane.colorsorting.repository.impl.LocalStorageImpl
import com.example.ckane.colorsorting.repository.impl.UserInfoRepositoryImpl
import com.example.ckane.colorsorting.util.createPowerUpList

class PowerUpStore : AppCompatActivity(), PowerUpView {
    private val userNameTextView: TextView by lazy { findViewById<TextView>(R.id.user_name_power_up_store) }
    private val moneyTextView: TextView by lazy { findViewById<TextView>(R.id.money_power_up_store) }

    private val sharedPref: SharedPreferences by lazy { this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE) }
    val repository: LocalStorage by lazy { LocalStorageImpl(sharedPref) }
    val db: AppDatabase by lazy { AppDatabase.getInstance(this) }
    private val userInfoRepository: UserInfoRepository by lazy { UserInfoRepositoryImpl(db) }
    private val presenter: StorePresenter by lazy { StorePresenterImpl(repository, userInfoRepository, this) }
    private val rView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.power_up_items_rc) }
    private val powerUpList: MutableList<PowerUpModel> = createPowerUpList()
    private val adapter: PowerUpRecyclerAdapter by lazy { PowerUpRecyclerAdapter(this, powerUpList, R.layout.power_up_item, presenter) }
    private val layoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_power_up_store)
        presenter.updateUserInfo()

        rView.itemAnimator = null
        rView.setHasFixedSize(true)
        rView.layoutManager = layoutManager
        rView.adapter = adapter
    }

    override fun setUserInfo(userInfo: UserInfo) {
        userNameTextView.text = userInfo.userName
        moneyTextView.text = resources.getString(R.string.coins, userInfo.money.toString())
        powerUpList[0].quantity = userInfo.powerUpA
        powerUpList[1].quantity = userInfo.powerUpB
        powerUpList[2].quantity = userInfo.powerUpC
        powerUpList[3].quantity = userInfo.powerUpD
        adapter.notifyDataSetChanged()
    }
}
