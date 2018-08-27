package com.example.ckane.colorsorting.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ckane.colorsorting.android.CardListManager
import com.example.ckane.colorsorting.android.CardViewHolderImpl
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.presentation.UpdateCardPresenter
import com.example.ckane.colorsorting.util.getCardDrawable

class RecyclerAdapter(private val context: Context,
                      private var cards: MutableList<Card>,
                      private val presenter: UpdateCardPresenter,
                      private val cardItemLayout : Int) : RecyclerView.Adapter<CardViewHolderImpl>(), CardListManager {

    lateinit var cardViewHolder : CardViewHolderImpl
    lateinit var layoutView : View
    private var viewClickable = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolderImpl {
        layoutView = LayoutInflater.from(parent.context).inflate(cardItemLayout, parent, false)
        Log.v("[VIEW CLICKABLE]", viewClickable.toString())
        cardViewHolder = CardViewHolderImpl(layoutView, presenter, viewClickable, presenter.isSoundOn(), presenter.isHapticOn())
        return cardViewHolder
    }

    override fun onBindViewHolder(holder: CardViewHolderImpl, position: Int) {
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
    override fun newData(newCards: MutableList<Card>, clickable: Boolean) {
        viewClickable = clickable
        cards = newCards
        notifyDataSetChanged()
    }

}