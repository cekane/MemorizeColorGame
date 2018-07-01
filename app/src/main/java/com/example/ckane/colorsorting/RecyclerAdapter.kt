package com.example.ckane.colorsorting

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.util.createCardList
import com.example.ckane.colorsorting.util.getCardDrawable

class RecyclerAdapter(private val context: Context, private var cards: MutableList<Card>) : RecyclerView.Adapter<CardViewHolder>(), CardListManager {
    private var savedColoredCards = mutableListOf<Card>()
    var adapterColorText = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardViewHolder(layoutView, this)
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
    private fun newCard(newCard: Card) {
        cards[newCard.position] = newCard
        notifyItemChanged(newCard.position)
    }

    /**
     * Updates adapters data to a new set of cards, and calls notifyDataSetChanged so that the all
     * cards on the UI are updated
     * @param newCards new list of cards to replace current list of cards
     */
    private fun newData(newCards: MutableList<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }

    /**
     * Function is from card manager, checks to see if the card the user clicked aligns with the
     * text at the top of the screen. If it does it "flips" that card over to it's proper color
     * @param position
     */
    override fun updateCard(position: Int) {
        if (savedColoredCards.isNotEmpty() && adapterColorText == savedColoredCards[position].backgroundColor) {
            newCard(Card(position, savedColoredCards[position].backgroundColor))
        }
    }

    /**
     * Function is from card manager, makes a new list of random colored cards and sets them to
     * the current data
     */
    override fun makeColors() {
        savedColoredCards = createCardList(false)
        newData(savedColoredCards)
    }

    /**
     * Function is from card manager, makes a new list of grey cards and sets them to
     * the current data
     */
    override fun makeGrey() =
            newData(createCardList(true))
}