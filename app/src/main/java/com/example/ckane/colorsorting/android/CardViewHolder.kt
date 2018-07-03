package com.example.ckane.colorsorting.android

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.presentation.CardPresenter

class CardViewHolder(layoutView: View, private val presenter: CardPresenter) : RecyclerView.ViewHolder(layoutView), View.OnClickListener {
    val cardImage: ImageView = layoutView.findViewById(R.id.card_image)

    init {
        layoutView.setOnClickListener(this)
    }

    override fun onClick(view: View) =
            presenter.updateCard(layoutPosition)
}
