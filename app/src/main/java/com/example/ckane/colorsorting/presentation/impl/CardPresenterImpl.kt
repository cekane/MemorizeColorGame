package com.example.ckane.colorsorting.presentation.impl

import android.util.Log
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.CardView
import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.presentation.CardPresenter
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.UserInfoRepository
import com.example.ckane.colorsorting.util.createCardList
import com.example.ckane.colorsorting.util.getColorFromNumber
import com.example.ckane.colorsorting.util.randomColorTextColor
import com.example.ckane.colorsorting.util.randomTextPosition
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Math.sqrt
import java.util.*

open class CardPresenterImpl(val view: CardView,
                             val repository: LocalStorage,
                             val userInfoRepository: UserInfoRepository) : CardPresenter {
    private var savedColoredCards = mutableListOf<Card>()
    private var wantedColors = mutableListOf<Card>()
    private var pickedColors = mutableListOf<Card>()
    private var adapterColorText = ""
    private var timerTime: Long = 1000
    private var textColor = "#FFFFFF"
    private var textPosition = 0
    private var deckSize = 16
    private var mode = ""
    private var shieldActivated: Boolean = false
    private var itemLayout = R.layout.card_item

    override fun getUserInfo() {
        userInfoRepository.getUserInfo(repository.getLocalUsername())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.enablePowerUps(it)
                }, {
                    view.enablePowerUps(UserInfo(""))
                })
    }

    override fun updatePowerUpAmount(powerUp: Int) {
        userInfoRepository.updatePowerUp(powerUp, repository.getLocalUsername(), -1)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.v("[CardPresenterImpl]", "Successfully updated powerups")
                }, {
                    Log.v("[CardPresenterImpl]", "Error updating powerups", it)
                })
    }

    override fun startRound() {
        //Picks the random color for the user
        val color = getColorFromNumber(Random().nextInt(4))
        view.setColorText(color, textPosition)
        view.setColorTextColor(textColor)

        //Makes the adapter create a random list of colored cards and displays them until post
        //delay is over
        makeColors(color)
        val makeGrey: () -> Unit = {
            view.newAdapter(createCardList(true, deckSize), sqrt(deckSize.toDouble()).toInt(), true, itemLayout)
        }
        boardToGrey(makeGrey)
    }

    /**
     * Function is from card manager, checks to see if the card the user clicked aligns with the
     * text at the top of the screen. If it does it "flips" that card over to it's proper color
     * @param position
     */
    override fun updateCard(position: Int) {
        if (savedColoredCards.isNotEmpty() && adapterColorText == savedColoredCards[position].backgroundColor) {
            wantedColors.removeIf { it.position == position }
            pickedColors.add(Card(position, savedColoredCards[position].backgroundColor))
            if (wantedColors.isEmpty()) {
                handleEndRound(position)
            } else {
                view.newCard(Card(position, savedColoredCards[position].backgroundColor))
            }

        } else if (!shieldActivated) {
            val finalScore = view.getCounterNumber()
            view.newAdapter(savedColoredCards, sqrt(deckSize.toDouble()).toInt(), false, itemLayout)
            view.endGame(finalScore)
        } else {
            //Player had a shield active and got one wrong
            shieldActivated = false
            pickedColors.add(Card(position, savedColoredCards[position].backgroundColor))
            view.newCard(Card(position, savedColoredCards[position].backgroundColor))
        }
    }

    private fun handleEndRound(position: Int){
        Completable.create {
            view.newCard(Card(position, savedColoredCards[position].backgroundColor))
            endRound()
            it.onComplete()
        }.subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.newAdapter(getEndingGreyTargetCards(pickedColors), sqrt(deckSize.toDouble()).toInt(), false, itemLayout)
            pickedColors = mutableListOf()
        }
    }

    private fun getEndingGreyTargetCards(pickedColors: MutableList<Card>): MutableList<Card> {
        val greyTempCards = createCardList(true, deckSize)
        pickedColors.forEach {
            greyTempCards[it.position] = it
        }
        return greyTempCards
    }

    private fun endRound() {
        val counterValue = (view.getCounterNumber() + 1)
        when (mode) {
            "CLASSIC_MODE_EASY" -> {
                updateTimer()
            }
            "CLASSIC_MODE_HARD" -> {
                updateTimer()
            }
            "CHALLENGE_MODE" -> challengeMode(counterValue)
        }
        view.setCounterText(counterValue.toString())
        view.roundEndFragment()
    }

    private fun updateTimer() {
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
                textColor = "#FFFFFF"
                deckSize = 25
                itemLayout = R.layout.card_item_smaller
//                view.newAdapter(createCardList(true, deckSize), sqrt(deckSize.toDouble()).toInt(), true, itemLayout)
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
        view.updateLocalHighScore(repository.getLocalHighScore(mode))
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
        view.newAdapter(savedColoredCards, sqrt(deckSize.toDouble()).toInt(), false, itemLayout)
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

    override fun activateShield() {
        shieldActivated = true
    }

    override fun replayBoard() {
        view.newAdapter(savedColoredCards, sqrt(deckSize.toDouble()).toInt(), false, itemLayout)
        val makeGrey: () -> Unit = {
            view.newAdapter(createCardList(true, deckSize), sqrt(deckSize.toDouble()).toInt(), true, itemLayout)
            pickedColors.forEach {
                view.newCard(it)
            }
        }
        boardToGrey(makeGrey)
    }

    override fun showOneColor() {
        val colorsToChoose = mutableListOf("blue", "green", "yellow", "red")
        colorsToChoose.removeIf { it == adapterColorText }
        val colorToChoose = colorsToChoose[Random().nextInt(3)]
        savedColoredCards.forEach {
            if (it.backgroundColor == colorToChoose) {
                pickedColors.add(it)
                view.newCard(it)
            }
        }
    }

    override fun showTargetedColor() {
        if (wantedColors.size == 1) {
            pickedColors.add(wantedColors[0])
            handleEndRound(wantedColors[0].position)
        } else {
            val chosenColorIndex = Random().nextInt(wantedColors.size)
            view.newCard(wantedColors[chosenColorIndex])
            pickedColors.add(wantedColors[chosenColorIndex])
            wantedColors.removeAt(chosenColorIndex)
        }
    }

    private fun boardToGrey(makeGrey: () -> Unit) {
        //The amount of time the user gets to remember the colors
        view.timer(timerTime, makeGrey)
    }

    override fun isSoundOn(): Boolean = repository.isSoundOn()
    override fun isHapticOn(): Boolean = repository.isHapticOn()

}