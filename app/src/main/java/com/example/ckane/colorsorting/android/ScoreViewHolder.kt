package com.example.ckane.colorsorting.android

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.ckane.colorsorting.R
import kotlinx.android.synthetic.main.score_item.view.*

class ScoreViewHolder(layoutView: View)  : RecyclerView.ViewHolder(layoutView), View.OnClickListener{
    val nameText : TextView = layoutView.findViewById(R.id.player_name)
    val scoreText : TextView = layoutView.findViewById(R.id.player_score)

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}