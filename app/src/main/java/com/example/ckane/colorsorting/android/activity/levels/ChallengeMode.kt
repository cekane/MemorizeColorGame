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
import com.example.ckane.colorsorting.util.createCardList

class ChallengeMode : AppCompatActivity(), CardView {
    private val presenter: CardPresenter = CardPresenterImpl(this)
    private var cardList: MutableList<Card> = createCardList(true, 16)
    private var rcAdapter = RecyclerAdapter(this, cardList, presenter, R.layout.card_item)
    private var gLayout = GridLayoutManager(this, 4)
    var color: TextView? = null
    var color2: TextView? = null
    var counter: TextView? = null
    var nextBtn: Button? = null
    var rView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.challenge_mode_activity)

        presenter.setGameMode(intent.getStringExtra("GAME_MODE"))

        rView = findViewById(R.id.recycler_view)
        rView?.itemAnimator = null
        color = findViewById(R.id.color_to_choose)
        color2 = findViewById(R.id.color_to_choose2)
        counter = findViewById(R.id.counter)
        nextBtn = findViewById(R.id.next_btn)
        nextBtn?.visibility = View.GONE

        nextBtn?.setOnClickListener {
            presenter.startRound()
            nextBtn?.visibility = View.GONE
        }

        rView?.setHasFixedSize(true)
        rView?.layoutManager = gLayout

        rView?.adapter = rcAdapter

        updateLocalHighScore()

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
                color?.text = colorText
                color2?.text = ""
            }
            1 -> {
                color?.text = ""
                color2?.text = colorText
            }
        }
    }

    override fun setColorTextColor(textColor: String) {
        color?.setTextColor(Color.parseColor(textColor))
        color2?.setTextColor(Color.parseColor(textColor))
    }

    override fun setCounterText(counterText: String) {
        counter?.text = counterText
    }

    override fun getCounterNumber(): Int = Integer.parseInt(counter?.text.toString())

    override fun timer(time: Long, f: () -> Unit) {
        Handler().postDelayed({
            f()
        }, time)
    }

    override fun endGame(score: Int) {
        startActivity(Intent(this, EndGame::class.java).apply {
            putExtra("FINAL_SCORE", score)
        })
    }

    override fun roundEndFragment() {
        setColorText(getString(R.string.friendly_message), 0)
        nextBtn?.visibility = View.VISIBLE
    }

    private fun updateLocalHighScore() {
        val sharedPref = this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.local_high_score), 0)
        findViewById<TextView>(R.id.high_score_value).text = highScore.toString()
    }

    override fun expandGrid(deckSize: Int, rowCount: Int) {
        cardList = createCardList(true, deckSize)
        rcAdapter = RecyclerAdapter(this, cardList, presenter, R.layout.card_item_smaller)
        gLayout = GridLayoutManager(this, rowCount)

        rView?.adapter = rcAdapter
        rView?.layoutManager = gLayout
    }
}

