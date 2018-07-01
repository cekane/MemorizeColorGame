package com.example.ckane.colorsorting

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

class CardViewHolder(layoutView: View, private val cardState: CardListManager) : RecyclerView.ViewHolder(layoutView), View.OnClickListener {
    val cardImage: ImageView = layoutView.findViewById(R.id.card_image)

    init {
        layoutView.setOnClickListener(this)
    }

    override fun onClick(view: View) =
            cardState.updateCard(layoutPosition)
}
