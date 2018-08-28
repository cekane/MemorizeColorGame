package com.example.ckane.colorsorting.android

import android.content.Context.VIBRATOR_SERVICE
import android.media.MediaPlayer
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.v7.widget.RecyclerView
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ImageView
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.presentation.UpdateCardPresenter

class CardViewHolderImpl(layoutView: View,
                         private val presenter: UpdateCardPresenter,
                         private val clickable: Boolean,
                         private val soundOn: Boolean,
                         private val hapticOn: Boolean) : RecyclerView.ViewHolder(layoutView) {
    val cardImage: ImageView = layoutView.findViewById(R.id.card_image)
    private val mediaPlayer = MediaPlayer.create(layoutView.context, R.raw.button_click_sound)
    init {
        if(this.clickable){
            cardImage.setOnClickListener {
                presenter.updateCard(layoutPosition)
                if(soundOn){
                    mediaPlayer.start()
                }
                if(hapticOn){
                    layoutView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                }
            }
        }
    }

}

