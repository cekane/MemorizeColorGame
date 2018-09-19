package com.ckane.colorflash.android.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ckane.colorflash.model.Card
import com.ckane.colorflash.util.getCardDrawable
import kotlin.math.sqrt

class RecyclerAdapterAnimated(private val context: Context,
                              private var cards: MutableList<Card>,
                              private val cardItemLayout: Int) : RecyclerView.Adapter<com.ckane.colorflash.android.activity.AnimateViewHolder>(), com.ckane.colorflash.android.CardListManager {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.ckane.colorflash.android.activity.AnimateViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(cardItemLayout, parent, false)
        val height = parent.height / sqrt(cards.size.toDouble()).toInt()
        val width = parent.width / sqrt(cards.size.toDouble()).toInt()
        val lp = layoutView.layoutParams as GridLayoutManager.LayoutParams
        lp.height = height
        lp.width = width
        layoutView.layoutParams = lp
        layoutView.minimumHeight = height
        layoutView.minimumWidth = width
        return com.ckane.colorflash.android.activity.AnimateViewHolder(layoutView)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: com.ckane.colorflash.android.activity.AnimateViewHolder, position: Int) {
        holder.cardImage.background = getCardDrawable(cards[position], context)
    }

    override fun newCard(newCard: Card) {
        //Nothing to do here
    }

    override fun newData(newCards: MutableList<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }

}