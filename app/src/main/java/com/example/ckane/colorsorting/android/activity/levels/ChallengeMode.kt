package com.example.ckane.colorsorting.android.activity.levels

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.CardView
import com.example.ckane.colorsorting.android.activity.EndGame
import com.example.ckane.colorsorting.android.adapter.RecyclerAdapter
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.presentation.CardPresenter
import com.example.ckane.colorsorting.presentation.impl.CardPresenterImpl
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.impl.LocalStorageImpl
import com.example.ckane.colorsorting.util.createCardList
import com.example.ckane.colorsorting.util.toDrawable

class ChallengeMode : AppCompatActivity(), CardView {

    private val presenter: CardPresenter = CardPresenterImpl(this)
    private var cardList: MutableList<Card> = createCardList(true, 16)
    private var rcAdapter = RecyclerAdapter(this, cardList, presenter, R.layout.card_item)
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
    private var gameMode = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.challenge_mode_activity)

        val sharedPref = this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE)
        val repository: LocalStorage = LocalStorageImpl(sharedPref)
        presenter.setRepository(repository)

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
            powerUpABtn.background = toDrawable(R.drawable.icon_activateshield_disabled,this)
            powerUpABtn.isEnabled = false
        }

        rView.setHasFixedSize(true)
        rView.layoutManager = gLayout

        rView.adapter = rcAdapter


        val startGame: () -> Unit = { presenter.startRound() }
        timer(1000, startGame)
    }

    override fun newData(newCards: MutableList<Card>) {
        rcAdapter.newData(newCards)
    }

    override fun newCard(newCard: Card) {
        rcAdapter.newCard(newCard)
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
            startActivity(Intent(this, EndGame::class.java).apply {
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

    private fun makePowerUpInvisible(){
        powerUpABtn.visibility = View.GONE
        powerUpBBtn.visibility = View.GONE
        powerUpCBtn.visibility = View.GONE
        powerUpDBtn.visibility = View.GONE
    }

    override fun updateLocalHighScore(highScore: Int) {
        findViewById<TextView>(R.id.high_score_value).text = highScore.toString()
    }

    override fun expandGrid(deckSize: Int, rowCount: Int) {
        cardList = createCardList(true, deckSize)
        rcAdapter = RecyclerAdapter(this, cardList, presenter, R.layout.card_item_smaller)
        gLayout = GridLayoutManager(this, rowCount)

        rView.adapter = rcAdapter
        rView.layoutManager = gLayout
    }

}

