package com.example.ckane.colorsorting

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var greyCardList : MutableList<Card> = createCardList(true)
        val adapter =  CardAdapter(this, greyCardList)
        val gridView: GridView = this.findViewById(R.id.gridview)
        var coloredCardList : MutableList<Card> = arrayListOf()

        val colorText : TextView = findViewById(R.id.color_to_choose)


        gridView.adapter = adapter
        gridView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    if(coloredCardList.isNotEmpty() && colorText.text == coloredCardList[position].backgroundColor ){
                        adapter.updateCardListItem(position, Card(position, coloredCardList[position].backgroundColor))
                        refreshGridView(adapter, gridView)
                    }
                }

        val gameControl: Button = this.findViewById(R.id.start_game)
        gameControl.setOnClickListener {

            greyCardList = createCardList(true)
            coloredCardList = createCardList(false)

            refreshGridView(adapter, gridView, coloredCardList)
            colorText.text = getColorFromNumber(Random().nextInt(4))
            Handler().postDelayed({
                refreshGridView(adapter, gridView, greyCardList)
            }, 1000)
        }
    }

    private fun refreshGridView(adapter : CardAdapter, gridView : GridView , cards : MutableList<Card> = arrayListOf() ){
        if(cards.isNotEmpty())
            adapter.updateCardList(cards)
        adapter.notifyDataSetChanged()
        gridView.invalidateViews()
    }

    private fun createCardList(isGrey : Boolean): MutableList<Card>{
        var randomNumber: Int
        return MutableList(16, {
            if(isGrey){
                Card(it, "grey")
            }
            else{
                randomNumber = Random().nextInt(4)
                Card(it, getColorFromNumber(randomNumber))
            }
        })
    }

    private fun getColorFromNumber(num : Int): String{
        when (num){
            0 -> return "red"
            1 -> return "blue"
            2 -> return "orange"
            3 -> return "yellow"
        }
        return ""
    }


}
