package com.example.ckane.colorsorting

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class CardAdapter(val context : Context, var cards : MutableList<Card>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = ImageView(context)
        val cardHeight = 250
        view.setBackgroundResource(getResourceBackgroundInt(position))
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, cardHeight)
        view.layoutParams = params

        return view
    }

    override fun getItem(position: Int): String =
        cards[position].backgroundColor


    override fun getItemId(position: Int): Long {
        //TODO: do something with this method
        return 0
    }

    override fun getCount(): Int {
        return cards.size
    }

    private fun getResourceBackgroundInt(position: Int): Int{
        when(cards[position].backgroundColor){
            "red" -> return R.drawable.ic_red_card
            "blue" -> return R.drawable.ic_blue_card
            "orange" -> return R.drawable.ic_orange_card
            "yellow" -> return  R.drawable.ic_yellow_card
            "grey" -> return R.drawable.ic_grey_card
        }
        return 0
    }

    fun updateCardListItem(position : Int , card : Card){
        cards[position] = card
    }

    fun updateCardList(newCards : MutableList<Card>){
        cards = newCards
    }
}