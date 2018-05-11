package com.example.ckane.colorsorting

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class CardAdapter(val context : Context, val cards : List<Int>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = ImageView(context)
        //TODO: need to make this dynamically 1/4 the size of the gridview
        val cardHeight = 250
        //TODO: find a way to change background color of vector file dynamically
        view.setBackgroundResource(R.drawable.ic_grey_card)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, cardHeight)
        view.layoutParams = params

        return view
    }

    override fun getItem(position: Int): Any =
        cards[position]


    override fun getItemId(position: Int): Long {
        //TODO: do something with this method
        return 0
    }

    override fun getCount(): Int {
        return cards.size
    }
}