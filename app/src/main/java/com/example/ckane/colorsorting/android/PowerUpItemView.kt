package com.example.ckane.colorsorting.android

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.ckane.colorsorting.R

class PowerUpItemView(layoutView : View) : RecyclerView.ViewHolder(layoutView) {
    val powerUpImage: ImageView = layoutView.findViewById(R.id.power_up_img)
    val powerUpDescription : TextView = layoutView.findViewById(R.id.power_up_description)
    val powerUpName : TextView = layoutView.findViewById(R.id.power_up_name)
    val powerUpQuantity : TextView = layoutView.findViewById(R.id.power_up_quantity)
    val powerUpCost : TextView = layoutView.findViewById(R.id.power_up_cost)
    val buyPowerUpBtn : Button = layoutView.findViewById(R.id.power_up_buy_btn)
}