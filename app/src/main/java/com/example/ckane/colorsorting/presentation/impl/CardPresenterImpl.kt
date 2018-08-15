package com.example.ckane.colorsorting.presentation.impl

import com.example.ckane.colorsorting.android.CardView
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.presentation.CardPresenter
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.util.createCardList
import com.example.ckane.colorsorting.util.getColorFromNumber
import com.example.ckane.colorsorting.util.randomColorTextColor
import com.example.ckane.colorsorting.util.randomTextPosition
import java.util.*

open class CardPresenterImpl(val view: CardView) : CardPresenter {

    private var savedColoredCards = mutableListOf<Card>()
    private var wantedColors = mutableListOf<Card>()
    private var adapterColorText = ""
    private var timerTime: Long = 1000
    private var textColor = "#FFFFFF"
    private var textPosition = 0
    private var deckSize = 16
    private var mode = ""
    private var repository: LocalStorage? = null

    override fun startRound() {
        //Picks the random color for the user
        val color = getColorFromNumber(Random().nextInt(4))
        view.setColorText(color, textPosition)
        view.setColorTextColor(textColor)

        //Makes the adapter create a random list of colored cards and displays them until post
        //delay is over
        makeColors(color)
        //The amount of time the user gets to remember the colors
        val makeGrey: () -> Unit = { view.newData(createCardList(true, deckSize)) }
        view.timer(timerTime, makeGrey)
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
                val counterValue = (view.getCounterNumber() + 1)
                when (mode) {
                    "CLASSIC_MODE_EASY" -> {
                        classicMode(counterValue, 1400)
                    }
                    "CLASSIC_MODE_HARD" -> {
                        classicMode(counterValue, 600)
                    }
                    "CHALLENGE_MODE" -> challengeMode(counterValue)
                }

                view.setCounterText(counterValue.toString())
                view.roundEndFragment()
            }
        } else {
            val finalScore = view.getCounterNumber()
            view.setCounterText("0")
            view.endGame(finalScore)
        }
    }

    private fun classicMode(counterValue: Int, startingTime: Int) {
        if (timerTime >= 200) {
            timerTime -= 10
        } else {
            timerTime = 200
        }
    }

    private fun challengeMode(counterValue: Int) {
        when (counterValue) {
        //Text Moves around
            in 6..10 -> {
                textPosition = randomTextPosition()
            }
        //Just Random Colors
            in 11..15 -> {
                textPosition = 0
                textColor = randomColorTextColor()
            }
        //Text Moves and Random color
            in 16..20 -> {
                textColor = randomColorTextColor()
                textPosition = randomTextPosition()
            }
        //5x5 Grid
            21 -> {
                textPosition = 0
                textColor = "#000000"
                deckSize = 25
                view.expandGrid(deckSize, 5)
            }
        //Text Moves around
            in 26..30 -> {
                textPosition = randomTextPosition()
            }
        //Just Random Colors
            in 31..35 -> {
                textPosition = 0
                textColor = randomColorTextColor()
            }
        //Text Moves and Random color
            in 36..40 -> {
                textColor = randomColorTextColor()
                textPosition = randomTextPosition()
            }
        }
    }

    override fun setGameMode(gameMode: String) {
        mode = gameMode
        repository?.let {
            view.updateLocalHighScore(it.getLocalHighScore(mode))
        }
    }

    override fun setGameTime() {
        when (mode) {
            "CLASSIC_MODE_EASY" -> timerTime = 1400
            "CLASSIC_MODE_HARD" -> timerTime = 600
            "CHALLENGE_MODE" -> timerTime = 1000
        }
    }

    /**
     * Function is from card manager, makes a new list of random colored cards and sets them to
     * the current data
     * @param color color that user is to remember
     */
    override fun makeColors(color: String) {
        adapterColorText = color
        savedColoredCards = createCardList(false, deckSize)
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

    override fun setRepository(repository: LocalStorage) {
        this.repository = repository
    }
}