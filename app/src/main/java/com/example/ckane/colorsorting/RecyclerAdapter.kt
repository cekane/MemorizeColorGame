package com.example.ckane.colorsorting

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class RecyclerAdapter(private val context: Context, private var cards: MutableList<Card>) : RecyclerView.Adapter<CardViewHolder>(), CardListManager {
    private var savedColoredCards = mutableListOf<Card>()
    var adapterColorText = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardViewHolder(layoutView, this)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.cardImage.background = getCardDrawable(cards, position, context)
    }

    override fun getItemCount(): Int = cards.size

    private fun newCard(newCard: Card) {
        cards[newCard.position] = newCard
        notifyItemChanged(newCard.position)
    }

    private fun newData(newCards: MutableList<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }

    override fun updateCard(position: Int) {
        if (savedColoredCards.isNotEmpty() && adapterColorText == savedColoredCards[position].backgroundColor) {
            newCard(Card(position, savedColoredCards[position].backgroundColor))
        }
    }

    override fun makeColors() {
        savedColoredCards = createCardList(false)
        newData(savedColoredCards)
    }

    override fun makeGrey() =
        newData(createCardList(true))
}

interface CardListManager {
    fun updateCard(position: Int)
    fun makeColors()
    fun makeGrey()
}