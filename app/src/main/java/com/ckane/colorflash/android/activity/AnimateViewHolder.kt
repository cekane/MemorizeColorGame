package com.ckane.colorflash.android.activity

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.ckane.colorflash.R

class AnimateViewHolder( layoutView : View) : RecyclerView.ViewHolder(layoutView){
    val cardImage: ImageView = layoutView.findViewById(R.id.card_image)
}