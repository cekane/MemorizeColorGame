package com.example.ckane.colorsorting.android.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.adapter.RecyclerAdapter
import com.example.ckane.colorsorting.android.CardView
import com.example.ckane.colorsorting.presentation.CardPresenter
import com.example.ckane.colorsorting.presentation.impl.CardPresenterImpl
import com.example.ckane.colorsorting.util.createCardList

class MainActivity : AppCompatActivity(), CardView {
    private val presenter: CardPresenter = CardPresenterImpl(this)
    private val cardList: MutableList<Card> = createCardList(true)
    private val rcAdapter = RecyclerAdapter(this, cardList, presenter)
    private val gLayout = GridLayoutManager(this, 4)
    var color : TextView? = null
    var counter : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rView: RecyclerView = findViewById(R.id.recycler_view)
        color = findViewById(R.id.color_to_choose)
        counter = findViewById(R.id.counter)

        rView.setHasFixedSize(true)
        rView.layoutManager = gLayout

        rView.adapter = rcAdapter
        startGameButton()
    }

    /**
     * Handles onClickListener for the start button. Tells adapter to create a colored list,
     * display it for x amount of time, and then switch back to grey cards. Also responsible for
     * choosing random color for a user to pay attention to
     */
    private fun startGameButton() {
        val gameControl: Button = this.findViewById(R.id.start_game)
        gameControl.setOnClickListener {
            it.visibility = View.GONE
            presenter.startRound()
        }
    }

    override fun newData(newCards: MutableList<Card>) {
        rcAdapter.newData(newCards)
    }

    override fun newCard(newCard: Card) {
        rcAdapter.newCard(newCard)
    }

    override fun setColorText(colorText: String) {
        color?.text = colorText
    }

    override fun setCounterText(counterText : String){
        counter?.text = counterText
    }

    override fun getCounterNumber() : Int = Integer.parseInt(counter?.text.toString())

    override fun timer(time : Long, f : () -> Unit ){
        Handler().postDelayed({
            f()
        }, time)
    }
}

