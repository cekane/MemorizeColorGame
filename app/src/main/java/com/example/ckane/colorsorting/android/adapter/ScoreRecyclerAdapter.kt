package com.example.ckane.colorsorting.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.ScoreViewHolder
import com.example.ckane.colorsorting.cache.entity.HighScore

class ScoreRecyclerAdapter(val context: Context, val highScores: List<HighScore>) : RecyclerView.Adapter<ScoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.score_item, parent, false)
        return ScoreViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.nameText.text = highScores[position].name
        holder.scoreText.text = highScores[position].score.toString()
    }

    override fun getItemCount(): Int = highScores.size
}