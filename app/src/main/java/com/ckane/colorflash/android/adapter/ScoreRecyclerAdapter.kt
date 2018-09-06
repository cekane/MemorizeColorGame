package com.ckane.colorflash.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ckane.colorflash.R
import com.ckane.colorflash.cache.entity.HighScore

class ScoreRecyclerAdapter(val context: Context, val highScores: List<HighScore>) : RecyclerView.Adapter<com.ckane.colorflash.android.ScoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.ckane.colorflash.android.ScoreViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.score_item, parent, false)
        return com.ckane.colorflash.android.ScoreViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: com.ckane.colorflash.android.ScoreViewHolder, position: Int) {
        holder.nameText.text = highScores[position].name
        holder.scoreText.text = highScores[position].score.toString()
    }

    override fun getItemCount(): Int = highScores.size
}