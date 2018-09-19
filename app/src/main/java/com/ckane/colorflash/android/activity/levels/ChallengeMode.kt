package com.ckane.colorflash.android.activity.levels

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.ckane.colorflash.R
import com.ckane.colorflash.android.adapter.RecyclerAdapter
import com.ckane.colorflash.cache.AppDatabase
import com.ckane.colorflash.cache.entity.UserInfo
import com.ckane.colorflash.model.Card
import com.ckane.colorflash.presentation.CardPresenter
import com.ckane.colorflash.presentation.impl.CardPresenterImpl
import com.ckane.colorflash.repository.LocalStorage
import com.ckane.colorflash.repository.UserInfoRepository
import com.ckane.colorflash.repository.impl.LocalStorageImpl
import com.ckane.colorflash.repository.impl.UserInfoRepositoryImpl
import com.ckane.colorflash.util.createCardList
import com.ckane.colorflash.util.toDrawable

class ChallengeMode : AppCompatActivity(), com.ckane.colorflash.android.CardView {
    private val sharedPref: SharedPreferences by lazy { this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE) }
    private val localStorage: LocalStorage by lazy { LocalStorageImpl(sharedPref) }
    val db: AppDatabase by lazy { AppDatabase.getInstance(this) }
    private val userInfoRepository: UserInfoRepository by lazy { UserInfoRepositoryImpl(db) }
    private val presenter: CardPresenter by lazy { CardPresenterImpl(this, localStorage, userInfoRepository) }
    private var cardList: MutableList<Card> = createCardList(true, 16)
    private var rcAdapter: RecyclerAdapter? = null
    private var gLayout = GridLayoutManager(this, 4)

    val color: TextView by lazy { findViewById<TextView>(R.id.color_to_choose) }
    private val color2: TextView by lazy { findViewById<TextView>(R.id.color_to_choose2) }
    val counter: TextView by lazy { findViewById<TextView>(R.id.counter) }
    private val nextBtn: Button by lazy { findViewById<Button>(R.id.next_btn) }
    private val powerUpABtn: Button by lazy { findViewById<Button>(R.id.power_up_A) }
    private val powerUpBBtn: Button by lazy { findViewById<Button>(R.id.power_up_B) }
    private val powerUpCBtn: Button by lazy { findViewById<Button>(R.id.power_up_C) }
    private val powerUpDBtn: Button by lazy { findViewById<Button>(R.id.power_up_D) }
    private val rView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private var mediaPlayer: MediaPlayer? = null
    private var gameMode = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.challenge_mode_activity)
        rcAdapter = RecyclerAdapter(this, cardList, presenter, R.layout.card_item)
        gameMode = intent.getStringExtra("GAME_MODE")
        presenter.setGameMode(gameMode)
        presenter.setGameTime()
        rView.itemAnimator = null
        nextBtn.visibility = View.GONE

        nextBtn.setOnClickListener {
            presenter.startRound()
            nextBtn.visibility = View.GONE
            powerUpABtn.visibility = View.VISIBLE
            powerUpBBtn.visibility = View.VISIBLE
            powerUpCBtn.visibility = View.VISIBLE
            powerUpDBtn.visibility = View.VISIBLE
        }

        //Activate shield button
        powerUpABtn.setOnClickListener {
            presenter.activateShield()
            handlePowerUpButton(powerUpABtn, R.drawable.icon_activateshield_disabled)
            presenter.updatePowerUpAmount(0)
        }

        //Replay board
        powerUpBBtn.setOnClickListener {
            presenter.replayBoard()
            handlePowerUpButton(powerUpBBtn, R.drawable.icon_showallcolors_disabled)
            presenter.updatePowerUpAmount(1)
        }

        //Show all of one color button
        powerUpCBtn.setOnClickListener {
            presenter.showOneColor()
            handlePowerUpButton(powerUpCBtn, R.drawable.icon_showdifferentcolor_disabled)
            presenter.updatePowerUpAmount(2)
        }

        //Show targeted color
        powerUpDBtn.setOnClickListener {
            presenter.showTargetedColor()
            handlePowerUpButton(powerUpDBtn, R.drawable.icon_showtargetcolor_disabled)
            presenter.updatePowerUpAmount(3)
        }
        rView.setHasFixedSize(true)
        rView.layoutManager = gLayout

        rView.adapter = rcAdapter
        presenter.getUserInfo()

        val startGame: () -> Unit = { presenter.startRound() }
        timer(1000, startGame)
    }

    override fun enablePowerUps(userInfo: UserInfo) {
        if (userInfo.powerUpA == 0) {
            handlePowerUpButton(powerUpABtn, R.drawable.icon_activateshield_disabled)
        }
        if (userInfo.powerUpB == 0) {
            handlePowerUpButton(powerUpBBtn, R.drawable.icon_showallcolors_disabled)
        }
        if (userInfo.powerUpC == 0) {
            handlePowerUpButton(powerUpCBtn, R.drawable.icon_showdifferentcolor_disabled)
        }
        if (userInfo.powerUpD == 0) {
            handlePowerUpButton(powerUpDBtn, R.drawable.icon_showtargetcolor_disabled)
        }
    }

    private fun handlePowerUpButton(btn: Button, backgroundImage: Int) {
        btn.background = toDrawable(backgroundImage, this)
        btn.isEnabled = false
    }

    override fun newData(newCards: MutableList<Card>) {
        rcAdapter?.newData(newCards)
    }

    override fun newCard(newCard: Card) {
        rcAdapter?.newCard(newCard)
    }


    override fun setColorText(colorText: String, textSelector: Int) {
        when (textSelector) {
            0 -> {
                color.text = colorText
                color2.text = ""
            }
            1 -> {
                color.text = ""
                color2.text = colorText
            }
        }
    }

    override fun setColorTextColor(textColor: String) {
        color.setTextColor(Color.parseColor(textColor))
        color2.setTextColor(Color.parseColor(textColor))
    }

    override fun setCounterText(counterText: String) {
        counter.text = counterText
    }

    override fun getCounterNumber(): Int = Integer.parseInt(counter.text.toString())

    override fun timer(time: Long, f: () -> Unit) {
        Handler().postDelayed({
            f()
        }, time)
    }

    override fun endGame(score: Int) {
        nextBtn.visibility = View.VISIBLE
        makePowerUpInvisible()
        nextBtn.setOnClickListener {
            startActivity(Intent(this, com.ckane.colorflash.android.activity.EndGame::class.java).apply {
                putExtra("FINAL_SCORE", score)
                putExtra("GAME_MODE", gameMode)
            })
        }
    }

    override fun roundEndFragment() {
        setColorText(getString(R.string.friendly_message), 0)
        nextBtn.visibility = View.VISIBLE
        makePowerUpInvisible()
    }

    private fun makePowerUpInvisible() {
        powerUpABtn.visibility = View.GONE
        powerUpBBtn.visibility = View.GONE
        powerUpCBtn.visibility = View.GONE
        powerUpDBtn.visibility = View.GONE
    }

    override fun updateLocalHighScore(highScore: Int) {
        findViewById<TextView>(R.id.high_score_value).text = highScore.toString()
    }

    override fun newAdapter(cardList : MutableList<Card>, rowCount: Int, clickable: Boolean, itemLayout: Int) {
        rcAdapter = RecyclerAdapter(this, cardList, presenter, itemLayout)
        if(gLayout.spanCount != rowCount){
            gLayout = GridLayoutManager(this, rowCount)
            rView.layoutManager = gLayout
        }
        rView.adapter = rcAdapter
    }

    override fun handleSound() {
        mediaPlayer = mediaPlayer?.let { mp ->
            if(mp.isPlaying){
                mp.stop()
                mp.release()
                MediaPlayer.create(this, R.raw.button_click_sound)
            }else{
                mediaPlayer
            }
        } ?: MediaPlayer.create(this, R.raw.button_click_sound)
        mediaPlayer?.start()
    }
}

