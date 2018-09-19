package com.ckane.colorflash.android

import android.support.v7.widget.RecyclerView
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ImageView
import com.ckane.colorflash.R
import com.ckane.colorflash.presentation.UpdateCardPresenter

class CardViewHolderImpl(layoutView: View,
                         private val presenter: UpdateCardPresenter,
                         private val soundOn: Boolean,
                         private val hapticOn: Boolean) : RecyclerView.ViewHolder(layoutView) {
    val cardImage: ImageView = layoutView.findViewById(R.id.card_image)
    init {
        cardImage.setOnClickListener { _ ->
            presenter.updateCard(layoutPosition, soundOn)
            if(hapticOn){
                layoutView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            }
        }
    }
}

