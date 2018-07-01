package com.example.ckane.colorsorting

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import java.util.*


fun getColorFromNumber(num: Int): String {
    when (num) {
        0 -> return "red"
        1 -> return "blue"
        2 -> return "orange"
        3 -> return "yellow"
    }
    return ""
}

fun createCardList(isGrey: Boolean): MutableList<Card> {
    var randomNumber: Int
    return MutableList(16, {
        if (isGrey) {
            Card(it, "grey")
        } else {
            randomNumber = Random().nextInt(4)
            Card(it, getColorFromNumber(randomNumber))
        }
    })
}

fun getCardDrawable(cards: MutableList<Card>, position: Int, context: Context): Drawable? {
    when (cards[position].backgroundColor) {
        "red" -> return toDrawable(R.drawable.ic_red_card, context)
        "blue" -> return toDrawable(R.drawable.ic_blue_card, context)
        "orange" -> return toDrawable(R.drawable.ic_orange_card, context)
        "yellow" -> return toDrawable(R.drawable.ic_yellow_card, context)
        "grey" -> return toDrawable(R.drawable.ic_grey_card, context)
    }
    return null
}

private fun toDrawable(resourceId: Int, context: Context): Drawable? = ContextCompat.getDrawable(context, resourceId)
