package com.ckane.colorflash.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ckane.colorflash.R
import com.ckane.colorflash.model.PowerUpModel
import com.ckane.colorflash.presentation.StorePresenter
import com.ckane.colorflash.util.toDrawable

class PowerUpRecyclerAdapter(private val context: Context,
                             private val powerUps: MutableList<PowerUpModel>,
                             private val powerUpItemLayout: Int,
                             private val presenter: StorePresenter) : RecyclerView.Adapter<com.ckane.colorflash.android.PowerUpItemView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.ckane.colorflash.android.PowerUpItemView {
        val layoutView = LayoutInflater.from(context).inflate(powerUpItemLayout, parent, false)
        return com.ckane.colorflash.android.PowerUpItemView(layoutView)
    }

    override fun getItemCount(): Int = powerUps.size

    override fun onBindViewHolder(holder: com.ckane.colorflash.android.PowerUpItemView, position: Int) {
        holder.powerUpImage.setImageDrawable(toDrawable(powerUps[position].imgSrc, context))
        holder.powerUpDescription.text = powerUps[position].description
        holder.powerUpQuantity.text = context.resources.getString(R.string.power_up_q, powerUps[position].quantity.toString())
        holder.buyPowerUpBtn.text = context.resources.getString(R.string.power_up_cost, powerUps[position].cost.toString())
        holder.buyPowerUpBtn.setOnClickListener {
            presenter.coinTransaction("BUY", powerUp = position)
        }
    }
}