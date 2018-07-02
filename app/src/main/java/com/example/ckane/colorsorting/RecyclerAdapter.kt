package com.example.ckane.colorsorting

import android.content.Context
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.util.createCardList
import com.example.ckane.colorsorting.util.getCardDrawable
import com.example.ckane.colorsorting.util.getColorFromNumber
import java.lang.Integer.parseInt
import java.util.*

//TODO Extract logic to presentation layer
class RecyclerAdapter(private val context: Context,
                      private var cards: MutableList<Card>,
                      private val counter: TextView,
                      private val colorText: TextView) : RecyclerView.Adapter<CardViewHolder>(), CardListManager {

    private var savedColoredCards = mutableListOf<Card>()
    private var wantedColors = mutableListOf<Card>()
    var adapterColorText = ""
    private val longTime: Long = 1000

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
            wantedColors.removeIf { it.position == position }
            newCard(Card(position, savedColoredCards[position].backgroundColor))
            if (wantedColors.isEmpty()) {
                Toast.makeText(context, "You did it", LENGTH_SHORT).show()
                counter.text = (parseInt(counter.text.toString()) + 1).toString()
                startRound()
            }
        } else {
            Toast.makeText(context, "You suck and you lost", LENGTH_SHORT).show()
            counter.text = "0"
            startRound()
        }
    }

    /**
     * Function is from card manager, makes a new list of random colored cards and sets them to
     * the current data
     * @param color color that user is to remember
     */
    override fun makeColors(color: String) {
        adapterColorText = color
        savedColoredCards = createCardList(false)
        createSingleColorList()
        newData(savedColoredCards)
    }

    /**
     * Filters through the list of colors and finds the unique ones.
     */
    private fun createSingleColorList() {
        var numColors = 0
        wantedColors = mutableListOf()
        savedColoredCards.forEach {
            if (it.backgroundColor == adapterColorText) {
                wantedColors.add(numColors, it)
                numColors++
            }
        }
    }

    /**
     * Function is from card manager, makes a new list of grey cards and sets them to
     * the current data
     */
    override fun makeGrey() =
            newData(createCardList(true))

    fun startRound() {
        //Picks the random color for the user
        val color = getColorFromNumber(Random().nextInt(4))
        colorText.text = color
        //Makes the adapter create a random list of colored cards and displays them until post
        //delay is over
        makeColors(color)
        //The amount of time the user gets to remember the colors
        Handler().postDelayed({
            makeGrey()
        }, longTime)
    }
}