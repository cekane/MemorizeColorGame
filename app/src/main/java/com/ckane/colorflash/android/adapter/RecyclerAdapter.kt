package com.ckane.colorflash.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ckane.colorflash.model.Card
import com.ckane.colorflash.presentation.UpdateCardPresenter
import com.ckane.colorflash.util.getCardDrawable

class RecyclerAdapter(private val context: Context,
                      private var cards: MutableList<Card>,
                      private val presenter: UpdateCardPresenter,
                      private val cardItemLayout : Int) : RecyclerView.Adapter<com.ckane.colorflash.android.CardViewHolderImpl>(), com.ckane.colorflash.android.CardListManager {

    lateinit var cardViewHolder : com.ckane.colorflash.android.CardViewHolderImpl
    lateinit var layoutView : View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.ckane.colorflash.android.CardViewHolderImpl {
        layoutView = LayoutInflater.from(parent.context).inflate(cardItemLayout, parent, false)
        cardViewHolder = com.ckane.colorflash.android.CardViewHolderImpl(layoutView, presenter, presenter.isSoundOn(), presenter.isHapticOn())
        return cardViewHolder
    }

    override fun onBindViewHolder(holder: com.ckane.colorflash.android.CardViewHolderImpl, position: Int) {
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