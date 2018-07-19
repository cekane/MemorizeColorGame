package com.example.ckane.colorsorting.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.R
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
fun createCardList(isGrey: Boolean, size : Int): MutableList<Card> {
    var randomNumber: Int
    return MutableList(size, {
        if (isGrey) {
            Card(it, "grey")
        } else {
            randomNumber = Random().nextInt(4)
            Card(it, getColorFromNumber(randomNumber))
        }
    })
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
private fun toDrawable(resourceId: Int, context: Context): Drawable? = ContextCompat.getDrawable(context, resourceId)

fun randomTextPosition(): Int = Random().nextInt(2)

