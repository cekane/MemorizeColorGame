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

        val colorText : TextView = findViewById(R.id.color_to_choose)

        val greyGrid =  CardAdapter(this, createCardList(true))
        val gridView: GridView = this.findViewById(R.id.gridview)
        gridView.adapter = greyGrid
//        gridView.onItemClickListener =
//                AdapterView.OnItemClickListener { parent, v, position, id ->
//                    val color = gridView.adapter.getItem(position)
//                    Toast.makeText(this, color.toString(), Toast.LENGTH_SHORT).show()
//                }

        val gameControl: Button = this.findViewById(R.id.start_game)
        gameControl.setOnClickListener {
            gridView.adapter = CardAdapter(this, createCardList(false))
            colorText.text = getColorFromNumber(Random().nextInt(4))
            Handler().postDelayed({
                gridView.adapter = greyGrid
                Toast.makeText(this, "Timer Works", Toast.LENGTH_SHORT).show()
            }, 1000)
        }
    }

    private fun createCardList(isGrey : Boolean): List<Card>{
        var randomNumber: Int
        return List(16, {
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
