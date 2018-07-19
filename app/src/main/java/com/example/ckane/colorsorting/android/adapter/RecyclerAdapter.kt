package com.example.ckane.colorsorting.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ckane.colorsorting.android.CardViewHolder
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.CardListManager
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.presentation.CardPresenter
import com.example.ckane.colorsorting.util.getCardDrawable

class RecyclerAdapter(private val context: Context,
                      private var cards: MutableList<Card>,
                      private val presenter: CardPresenter,
                      private val cardItemLayout : Int) : RecyclerView.Adapter<CardViewHolder>(), CardListManager {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(cardItemLayout, parent, false)
        return CardViewHolder(layoutView, presenter)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.cardImage.background = getCardDrawable(cards[position], context)
    }

    override fun getItemCount(): Int = cards.size

    /**
     * Updates adapters data to a new card, and calls notifyItemChanged so that the UI only updates
     * that specific card
     * @param newCard the new card to be updated
     */
    override fun newCard(newCard: Card) {
        cards[newCard.position] = newCard
        notifyItemChanged(newCard.position)
    }

    /**
     * Updates adapters data to a new set of cards, and calls notifyDataSetChanged so that the all
     * cards on the UI are updated
     * @param newCards new list of cards to replace current list of cards
     */
    override fun newData(newCards: MutableList<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }
}