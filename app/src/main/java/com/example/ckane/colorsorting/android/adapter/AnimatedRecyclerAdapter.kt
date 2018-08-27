package com.example.ckane.colorsorting.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ckane.colorsorting.android.CardListManager
import com.example.ckane.colorsorting.android.activity.AnimateViewHolder
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.util.getCardDrawable

class RecyclerAdapterAnimated(private val context: Context,
                              private var cards: MutableList<Card>,
                              private val cardItemLayout: Int) : RecyclerView.Adapter<AnimateViewHolder>(), CardListManager {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimateViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(cardItemLayout, parent, false)
        return AnimateViewHolder(layoutView)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: AnimateViewHolder, position: Int) {
        holder.cardImage.background = getCardDrawable(cards[position], context)
    }

    override fun newCard(newCard: Card) {
        //Nothing to do here
    }

    override fun newData(newCards: MutableList<Card>, clickable: Boolean) {
        cards = newCards
        notifyDataSetChanged()
    }

}