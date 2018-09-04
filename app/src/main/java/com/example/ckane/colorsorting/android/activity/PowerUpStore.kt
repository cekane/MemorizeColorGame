package com.example.ckane.colorsorting.android.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener

class PowerUpStore : AppCompatActivity(), PowerUpView, RewardedVideoAdListener {
    private val moneyTextView: TextView by lazy { findViewById<TextView>(R.id.money_power_up_store) }

    private val sharedPref: SharedPreferences by lazy { this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE) }
    val repository: LocalStorage by lazy { LocalStorageImpl(sharedPref) }
    val db: AppDatabase by lazy { AppDatabase.getInstance(this) }
    private val userInfoRepository: UserInfoRepository by lazy { UserInfoRepositoryImpl(db) }
    private val powerUpList: MutableList<PowerUpModel> = createPowerUpList()
    private val presenter: StorePresenter by lazy { StorePresenterImpl(repository, userInfoRepository, this, powerUpList) }
    private val rView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.power_up_items_rc) }
    private val adapter: PowerUpRecyclerAdapter by lazy { PowerUpRecyclerAdapter(this, powerUpList, R.layout.power_up_item, presenter) }
    private val layoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    private val videoForCoins: Button by lazy { findViewById<Button>(R.id.watch_video_for_coins) }
    private lateinit var rewardedVideoAd: RewardedVideoAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_power_up_store)
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        rewardedVideoAd.rewardedVideoAdListener = this
        videoForCoins.setOnClickListener {
            rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                    AdRequest.Builder().build())

        }

        presenter.updateUserInfo()

        rView.itemAnimator = null
        rView.setHasFixedSize(true)
        rView.layoutManager = layoutManager
        rView.adapter = adapter
    }

    override fun setUserInfo(userInfo: UserInfo) {
        moneyTextView.text = resources.getString(R.string.coins, userInfo.money.toString())
        powerUpList[0].quantity = userInfo.powerUpA
        powerUpList[1].quantity = userInfo.powerUpB
        powerUpList[2].quantity = userInfo.powerUpC
        powerUpList[3].quantity = userInfo.powerUpD
        adapter.notifyDataSetChanged()
    }

    override fun onRewardedVideoAdClosed() {
    }

    override fun onRewardedVideoAdLeftApplication() {
    }

    override fun onRewardedVideoAdLoaded() {
        rewardedVideoAd.show()
    }

    override fun onRewardedVideoAdOpened() {
    }

    override fun onRewardedVideoCompleted() {
    }

    override fun onRewarded(p0: RewardItem?) {
        presenter.coinTransaction("ADD", ammount = 50)
    }

    override fun onRewardedVideoStarted() {
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
    }
}
