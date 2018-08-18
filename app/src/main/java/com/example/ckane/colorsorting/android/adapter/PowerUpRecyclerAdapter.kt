package com.example.ckane.colorsorting.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.PowerUpItemView
import com.example.ckane.colorsorting.model.PowerUpModel
import com.example.ckane.colorsorting.presentation.StorePresenter
import com.example.ckane.colorsorting.util.toDrawable

class PowerUpRecyclerAdapter(private val context: Context,
                             private val powerUps: MutableList<PowerUpModel>,
                             private val powerUpItemLayout: Int,
                             private val presenter: StorePresenter) : RecyclerView.Adapter<PowerUpItemView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PowerUpItemView {
        val layoutView = LayoutInflater.from(context).inflate(powerUpItemLayout, parent, false)
        return PowerUpItemView(layoutView)
    }

    override fun getItemCount(): Int = powerUps.size

    override fun onBindViewHolder(holder: PowerUpItemView, position: Int) {
        holder.powerUpImage.setImageDrawable(toDrawable(powerUps[position].imgSrc, context))
        holder.powerUpName.text = powerUps[position].powerUpName
        holder.powerUpDescription.text = powerUps[position].description
        holder.powerUpQuantity.text = context.resources.getString(R.string.power_up_q, powerUps[position].quantity.toString())
        holder.powerUpCost.text = context.resources.getString(R.string.power_up_cost, powerUps[position].cost.toString())
        holder.buyPowerUpBtn.setOnClickListener {
            presenter.buyPowerUp(position)
        }
    }
}