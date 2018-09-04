package com.example.ckane.colorsorting.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.model.PowerUpModel
import java.util.*

/**
 * takes in a number returns it's matching color
 * @param num the number to be converted to a string color
 */
fun getColorFromNumber(num: Int): String {
    when (num) {
        0 -> return "red"
        1 -> return "blue"
        2 -> return "green"
        3 -> return "yellow"
    }
    return ""
}

fun getHexFromNumber(num: Int): String {
    when (num) {
        0 -> return "#F56154"
        1 -> return "#4F87B3"
        2 -> return "#87B34F"
        3 -> return "#F2C238"
    }
    return ""
}

/**
 * Creates a card list of either random colors or all grey
 * @param isGrey when true creates a grey list of cards, when false creates a colored list
 */
fun createCardList(isGrey: Boolean, size: Int): MutableList<Card> {
    var randomNumber: Int
    val cardList = MutableList(size) {
        if (isGrey) {
            Card(it, "grey")
        } else {
            randomNumber = Random().nextInt(4)
            Card(it, getColorFromNumber(randomNumber))
        }
    }
    if(!isGrey) {
        val allColors = arrayOf("red", "blue", "green", "yellow")
        val spotsPicked = mutableListOf(0)
        allColors.forEach {
            val randNum = getUniqueRandomNumber(spotsPicked, size)
            spotsPicked.add(randNum)
            cardList[randNum].backgroundColor = it
        }
    }
    return cardList
}

fun getUniqueRandomNumber(picked: List<Int>, size: Int): Int{
    val randNum = Random().nextInt(size)
    return if(!picked.contains(randNum)){
        randNum
    } else{
        getUniqueRandomNumber(picked, size)
    }
}

/**
 * Gets the vector drawables of each colored card depending on the position passed in
 * @param card the card that we want the drawable for based on it's background color
 * @param context need to use the toDrawable function
 */
fun getCardDrawable(card: Card, context: Context): Drawable? {
    when (card.backgroundColor) {
        "red" -> return toDrawable(R.drawable.ic_red_card, context)
        "blue" -> return toDrawable(R.drawable.ic_blue_card, context)
        "green" -> return toDrawable(R.drawable.ic_green_card, context)
        "yellow" -> return toDrawable(R.drawable.ic_yellow_card, context)
        "grey" -> return toDrawable(R.drawable.ic_grey_card, context)
    }
    return null
}

fun randomColorTextColor(): String = getHexFromNumber(Random().nextInt(4))

/**
 * converts resource id to vector drawable
 */
fun toDrawable(resourceId: Int, context: Context): Drawable? = ContextCompat.getDrawable(context, resourceId)

fun randomTextPosition(): Int = Random().nextInt(2)

fun createPowerUpList(): MutableList<PowerUpModel> = MutableList(4) {
    when (it) {
        0 -> PowerUpModel(R.drawable.icon_activateshield, "Shield", "Protects you from one wrong move", 0, 200)
        1 -> PowerUpModel(R.drawable.icon_showallcolors, "Replay", "Shows all color tiles again", 0, 150)
        2 -> PowerUpModel(R.drawable.icon_showdifferentcolor, "Show", "Reveals all tiles of one color", 0, 50)
        3 -> PowerUpModel(R.drawable.icon_showtargetcolor, "Target", "Reveals one correct color tile", 0, 100)
        else -> PowerUpModel(0, "", "", 0, 0)
    }
}

fun createHowToList(): MutableList<Card> = MutableList<Card>(16) {
    when (it) {
        0 -> Card(0, "blue")
        1 -> Card(1, "red")
        2 -> Card(2, "blue")
        3 -> Card(3, "yellow")
        4 -> Card(4, "yellow")
        5 -> Card(5, "yellow")
        6 -> Card(6, "red")
        7 -> Card(7, "red")
        8 -> Card(8, "yellow")
        9 -> Card(9, "red")
        10 -> Card(10, "blue")
        11 -> Card(11, "yellow")
        12 -> Card(12, "yellow")
        13 -> Card(13, "red")
        14 -> Card(14, "green")
        15 -> Card(15, "red")
        else->  Card(0, "blue")
    }
}

