package com.example.ckane.colorsorting.android

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.presentation.UpdateCardPresenter
class CardViewHolderImpl(layoutView: View, private val presenter: UpdateCardPresenter, private val clickable : Boolean) : RecyclerView.ViewHolder(layoutView){
    val cardImage: ImageView = layoutView.findViewById(R.id.card_image)
    init{
        cardImage.setOnClickListener { presenter.updateCard(layoutPosition) }
    }

}

