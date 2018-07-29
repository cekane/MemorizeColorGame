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

class ChallengeMode : AppCompatActivity(), CardView {


    private val presenter: CardPresenter = CardPresenterImpl(this)
    private var cardList: MutableList<Card> = createCardList(true, 16)
    private var rcAdapter = RecyclerAdapter(this, cardList, presenter, R.layout.card_item)
    private var gLayout = GridLayoutManager(this, 4)
    var color: TextView? = null
    private var color2: TextView? = null
    var counter: TextView? = null
    private var nextBtn: Button? = null
    private var rView: RecyclerView? = null
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
            putExtra("GAME_MODE", gameMode)
        })
    }

    override fun roundEndFragment() {
        setColorText(getString(R.string.friendly_message), 0)
        nextBtn?.visibility = View.VISIBLE
    }

    override fun updateLocalHighScore(highScore: Int) {
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

