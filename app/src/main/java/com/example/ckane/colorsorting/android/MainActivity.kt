package com.example.ckane.colorsorting.android

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.RecyclerAdapter
import com.example.ckane.colorsorting.util.createCardList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardList: MutableList<Card> = createCardList(true)
        val rcAdapter = RecyclerAdapter(this, cardList, findViewById(R.id.counter), findViewById<TextView>(R.id.color_to_choose))
        val gLayout = GridLayoutManager(this, 4)
        val rView = findViewById<RecyclerView>(R.id.recycler_view)
        rView.setHasFixedSize(true)
        rView.layoutManager = gLayout

        rView.adapter = rcAdapter
        startGameButton(rcAdapter)

    }

    /**
     * Handles onClickListener for the start button. Tells adapter to create a colored list,
     * display it for x amount of time, and then switch back to grey cards. Also responsible for
     * choosing random color for a user to pay attention to
     * @param adapter the adapter controls whats actually going on for the view
     */
    private fun startGameButton(adapter: RecyclerAdapter){
        val colorText: TextView = findViewById(R.id.color_to_choose)
        val gameControl: Button = this.findViewById(R.id.start_game)
        gameControl.setOnClickListener {
            it.visibility = View.GONE
            adapter.startRound()
        }
    }
}

