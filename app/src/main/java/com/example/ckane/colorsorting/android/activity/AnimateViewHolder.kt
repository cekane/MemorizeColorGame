package com.example.ckane.colorsorting.android.activity

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.example.ckane.colorsorting.R

class AnimateViewHolder( layoutView : View) : RecyclerView.ViewHolder(layoutView){
    val cardImage: ImageView = layoutView.findViewById(R.id.card_image)
}