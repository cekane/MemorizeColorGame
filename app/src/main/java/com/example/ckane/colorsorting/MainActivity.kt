package com.example.ckane.colorsorting

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val greyCardList: MutableList<Card> = createCardList(true)
        val rcAdapter = RecyclerAdapter(this, greyCardList)
        val gLayout = GridLayoutManager(this, 4)

        val rView = findViewById<RecyclerView>(R.id.recycler_view)
        rView.setHasFixedSize(true)
        rView.layoutManager = gLayout

        rView.adapter = rcAdapter

        val colorText: TextView = findViewById(R.id.color_to_choose)
        val gameControl: Button = this.findViewById(R.id.start_game)
        gameControl.setOnClickListener {
            rcAdapter.makeColors()
            rcAdapter.adapterColorText = getColorFromNumber(Random().nextInt(4))
            colorText.text = rcAdapter.adapterColorText

            Handler().postDelayed({
                rcAdapter.makeGrey()
            }, 1000)
        }
    }

}

