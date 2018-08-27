package com.example.ckane.colorsorting.android.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.adapter.RecyclerAdapter
import com.example.ckane.colorsorting.model.Card
import com.example.ckane.colorsorting.presentation.UpdateCardPresenter
import com.example.ckane.colorsorting.util.createCardList
import com.example.ckane.colorsorting.util.createHowToList
import com.example.ckane.colorsorting.util.toDrawable
import java.util.*

private const val ARG_POSITION = "position"
private const val ARG_INSTRUCTIONS = "instructions"

class HowToPlayActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_play)

        val pager = findViewById<ViewPager>(R.id.how_to_viewpager)
        val adapter = HowToAdapter(supportFragmentManager)
        pager.adapter = adapter
    }
}

class HowToAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val fragment = HowToFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_POSITION, position)
            when (position) {
                0 -> putInt(ARG_INSTRUCTIONS, R.string.how_to_1_instructions)
                1 -> putInt(ARG_INSTRUCTIONS, R.string.how_to_2_instructions)
                2 -> putInt(ARG_INSTRUCTIONS, R.string.how_to_3_instructions)
                3 -> putInt(ARG_INSTRUCTIONS, R.string.how_to_4_instructions)
            }
        }
        return fragment
    }

    override fun getCount(): Int = 4

}

class HowToFragment : Fragment(), HowToCardView {
    private val howToCards = createHowToList()
    private val presenter: HowToPresenter by lazy {
        HowToPresenterImpl(howToCards, this)
    }
    private lateinit var instructionText : TextView
    private lateinit var rcAdapter : RecyclerAdapter
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(
                R.layout.fragment_how_to, container, false
        )
        var position = 0
        arguments?.takeIf { it.containsKey(ARG_POSITION) }?.apply {
            position = getInt(ARG_POSITION)
        }
        //Sets the color to choose at the top
        if(position!= 3){
            val textView = rootView.findViewById<TextView>(R.id.how_to_color_choice)
            textView.text = getString(R.string.how_to_red)
        }

        val cardList: MutableList<Card> = if (position == 0) {
            howToCards
        } else {
            createCardList(true, 16)
        }
        presenter.createSingleColorList()
        rcAdapter = RecyclerAdapter(rootView.context, cardList, presenter, R.layout.card_item)
        val gLayout = GridLayoutManager(rootView.context, 4)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)

        //Sets the instructions at the bottom of each page
        arguments?.takeIf { it.containsKey(ARG_INSTRUCTIONS) }?.apply {
            instructionText = rootView.findViewById(R.id.instructions)
            if(position == 3) {
                val endTutorialText = rootView.findViewById<TextView>(R.id.end_tutorial_text)
                endTutorialText.text = getString(getInt(ARG_INSTRUCTIONS))
            }else{
                instructionText.text = getString(getInt(ARG_INSTRUCTIONS))
            }
        }

        //Initializes power-ups
        //Activate shield button
        val powerUpABtn = rootView.findViewById<Button>(R.id.power_up_A2)
        powerUpABtn.setOnClickListener {
            presenter.activateShield()
            handlePowerUpButton(powerUpABtn, R.drawable.icon_activateshield_disabled, rootView.context)
        }

        //Replay board
        val powerUpBBtn = rootView.findViewById<Button>(R.id.power_up_B2)
        powerUpBBtn.setOnClickListener {
            presenter.replayBoard()
            handlePowerUpButton(powerUpBBtn, R.drawable.icon_showallcolors_disabled, rootView.context)
        }

        //Show all of one color button
        val powerUpCBtn = rootView.findViewById<Button>(R.id.power_up_C2)
        powerUpCBtn.setOnClickListener {
            presenter.showOneColor()
            handlePowerUpButton(powerUpCBtn, R.drawable.icon_showdifferentcolor_disabled, rootView.context)
        }

        //Show targeted color
        val powerUpDBtn = rootView.findViewById<Button>(R.id.power_up_D2)
        powerUpDBtn.setOnClickListener {
            presenter.showTargetedColor()
            handlePowerUpButton(powerUpDBtn, R.drawable.icon_showtargetcolor_disabled, rootView.context)
        }

        if(position != 2){
            powerUpABtn.visibility = View.GONE
            powerUpBBtn.visibility = View.GONE
            powerUpCBtn.visibility = View.GONE
            powerUpDBtn.visibility = View.GONE
        }

        //Initializes recycler adapter with cards
        if(position!=3){
            recyclerView.itemAnimator = null
            recyclerView.adapter = rcAdapter
            recyclerView.layoutManager = gLayout
            recyclerView.setHasFixedSize(true)
        }else{
            val letsPlay = rootView.findViewById<Button>(R.id.lets_play)
            letsPlay.visibility = View.VISIBLE
            letsPlay.setOnClickListener {
                startActivity(Intent(rootView.context, MenuActivity::class.java))
            }
        }

        return rootView
    }

    override fun newCard(card: Card) {
        rcAdapter.newCard(card)
    }

    override fun newInstructionText(instructionId: Int) {
        instructionText.text = getString(instructionId)
    }

    private fun handlePowerUpButton(btn: Button, backgroundImage: Int, context : Context) {
        btn.background = toDrawable(backgroundImage, context)
        btn.isEnabled = false
    }

    override fun newData(cards: MutableList<Card>) {
        rcAdapter.newData(cards, true)
    }

    override fun boardToGrey(makeGrey: () -> Unit) {
        Handler().postDelayed(makeGrey, 1000)
    }
}

class HowToPresenterImpl(private val howToCards : MutableList<Card>, val view: HowToCardView) : HowToPresenter {
    private var savedColoredCards = mutableListOf<Card>()
    private var pickedColors = mutableListOf<Card>()
    private var shieldActivated: Boolean = false
    override fun updateCard(position: Int) {
        if (savedColoredCards.isNotEmpty() && "red" == howToCards[position].backgroundColor)
        {
            view.newCard(Card(position, howToCards[position].backgroundColor))
            pickedColors.add(Card(position, howToCards[position].backgroundColor))
            savedColoredCards.removeIf { it.position == position }
            if(savedColoredCards.size == 0){
                view.newInstructionText(R.string.how_to_complete_instructions)
            }
        }else if(shieldActivated){
            shieldActivated = false
            pickedColors.add(Card(position, howToCards[position].backgroundColor))
            view.newCard(Card(position, howToCards[position].backgroundColor))
        }
    }

    override fun createSingleColorList() {
        var numColors = 0
        howToCards.forEach {
            if (it.backgroundColor == "red") {
                savedColoredCards.add(numColors, it)
                numColors++
            }
        }
    }
    override fun activateShield() {
        shieldActivated = true
    }

    override fun replayBoard() {
        view.newData(howToCards)
        val makeGrey: () -> Unit = {
            view.newData(createCardList(true, 16))
            pickedColors.forEach {
                view.newCard(it)
            }
        }
        view.boardToGrey(makeGrey)
    }

    override fun showOneColor() {
        val colorsToChoose = mutableListOf("blue", "green", "yellow", "red")
        colorsToChoose.removeIf { it == "red" }
        val colorToChoose = colorsToChoose[Random().nextInt(3)]
        howToCards.forEach {
            if (it.backgroundColor == colorToChoose) {
                pickedColors.add(it)
                view.newCard(it)
            }
        }
    }

    override fun showTargetedColor() {
        if (savedColoredCards.size == 1) {
            view.newCard(savedColoredCards[0])
        } else if( savedColoredCards.size > 0 ) {
            val chosenColorIndex = Random().nextInt(savedColoredCards.size)
            view.newCard(savedColoredCards[chosenColorIndex])
            pickedColors.add(savedColoredCards[chosenColorIndex])
            savedColoredCards.removeAt(chosenColorIndex)
        }
    }
}

interface HowToCardView{
    fun newCard(card : Card)
    fun newData(cards: MutableList<Card>)
    fun newInstructionText(instructionId : Int)
    fun boardToGrey(makeGrey: () -> Unit)
}

interface HowToPresenter : UpdateCardPresenter{
    fun createSingleColorList()
    fun activateShield()
    fun replayBoard()
    fun showOneColor()
    fun showTargetedColor()
}
