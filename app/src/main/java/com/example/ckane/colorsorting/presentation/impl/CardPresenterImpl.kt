package com.example.ckane.colorsorting.presentation.impl

import com.example.ckane.colorsorting.android.CardView
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.presentation.CardPresenter
import com.example.ckane.colorsorting.util.createCardList
import com.example.ckane.colorsorting.util.getColorFromNumber
import java.util.*

class CardPresenterImpl(val view : CardView) : CardPresenter {

    private var savedColoredCards = mutableListOf<Card>()
    private var wantedColors = mutableListOf<Card>()
    var adapterColorText = ""
    private val longTime: Long = 1000

    override fun startRound() {
        //Picks the random color for the user
        val color = getColorFromNumber(Random().nextInt(4))
        view.setColorText(color)
        //Makes the adapter create a random list of colored cards and displays them until post
        //delay is over
        makeColors(color)
        //The amount of time the user gets to remember the colors
        val makeGrey : ()-> Unit = {view.newData(createCardList(true))}
        view.timer(longTime, makeGrey)
    }

    /**
     * Function is from card manager, checks to see if the card the user clicked aligns with the
     * text at the top of the screen. If it does it "flips" that card over to it's proper color
     * @param position
     */
    override fun updateCard(position: Int) {
        if (savedColoredCards.isNotEmpty() && adapterColorText == savedColoredCards[position].backgroundColor) {
            wantedColors.removeIf { it.position == position }
            view.newCard(Card(position, savedColoredCards[position].backgroundColor))
            if (wantedColors.isEmpty()) {

                view.setCounterText((view.getCounterNumber() + 1).toString())
                startRound()
            }
        } else {
            view.setCounterText("0")
            startRound()
        }
    }

    /**
     * Function is from card manager, makes a new list of random colored cards and sets them to
     * the current data
     * @param color color that user is to remember
     */
    override fun makeColors(color: String) {
        adapterColorText = color
        savedColoredCards = createCardList(false)
        createSingleColorList()
        view.newData(savedColoredCards)
    }

    /**
     * Filters through the list of colors and finds the unique ones.
     */
    override fun createSingleColorList() {
        var numColors = 0
        wantedColors = mutableListOf()
        savedColoredCards.forEach {
            if (it.backgroundColor == adapterColorText) {
                wantedColors.add(numColors, it)
                numColors++
            }
        }
    }
}