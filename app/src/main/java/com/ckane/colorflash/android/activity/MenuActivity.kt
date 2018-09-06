package com.ckane.colorflash.android.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import com.ckane.colorflash.R
import com.ckane.colorflash.android.adapter.RecyclerAdapterAnimated
import com.ckane.colorflash.cache.AppDatabase
import com.ckane.colorflash.cache.AppDatabase.Companion.getInstance
import com.ckane.colorflash.model.Card
import com.ckane.colorflash.presentation.MainMenuPresenter
import com.ckane.colorflash.presentation.impl.MainMenuPresenterImpl
import com.ckane.colorflash.repository.LocalStorage
import com.ckane.colorflash.repository.UserInfoRepository
import com.ckane.colorflash.repository.impl.LocalStorageImpl
import com.ckane.colorflash.repository.impl.UserInfoRepositoryImpl
import com.ckane.colorflash.util.createCardList
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.games.Games
import com.google.android.gms.tasks.Task

class MenuActivity : AppCompatActivity(), com.ckane.colorflash.android.MenuView {
    private val sharedPref: SharedPreferences by lazy {
        this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE)
    }
    val repository: LocalStorage by lazy {
        LocalStorageImpl(sharedPref)
    }
    val db: AppDatabase by lazy {
        getInstance(this)
    }
    private val userInfoRepository: UserInfoRepository by lazy {
        UserInfoRepositoryImpl(db)
    }
    private val presenter: MainMenuPresenter by lazy {
        MainMenuPresenterImpl(repository, userInfoRepository, this)
    }
    private val rView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private var animateAdapter = RecyclerAdapterAnimated(this, createCardList(false, 16), R.layout.card_item)
    private var gLayout = GridLayoutManager(this, 4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        rView.itemAnimator = null
        rView.setHasFixedSize(true)
        rView.layoutManager = gLayout
        rView.adapter = animateAdapter

        presenter.setUpAnimatedView()

        val updateTimerBtn: Button = findViewById(R.id.play_game)
        updateTimerBtn.setOnClickListener {
            startActivity(Intent(this, com.ckane.colorflash.android.activity.DifficultyPickerActivity::class.java))
        }

        val howToButton: Button = findViewById(R.id.how_to_play)
        howToButton.setOnClickListener {
            startActivity(Intent(this, HowToPlayActivity::class.java))
        }

        val storeButton: Button = findViewById(R.id.game_store)
        storeButton.setOnClickListener {
            startActivity(Intent(this, PowerUpStore::class.java))
        }

        val settingsButton: Button = findViewById(R.id.game_settings)
        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        val scoreboardButton: Button = findViewById(R.id.game_scoreboard)
        scoreboardButton.setOnClickListener {
            val account = GoogleSignIn.getLastSignedInAccount(this)
            account?.let { noNullAccount ->
                Games.getLeaderboardsClient(this, noNullAccount)
                        .allLeaderboardsIntent
                        .addOnSuccessListener { intent ->
                            startActivityForResult(intent, 9004)
                        }
            }
        }


        signIn()

    }

    private fun updateUI(account: GoogleSignInAccount) {
        account.displayName?.let{
            presenter.handleRegistration(it)
        } ?: presenter.handleRegistration("")
    }

    override fun updateCards(cards: MutableList<Card>) {
        animateAdapter.newData(cards)
    }

    override fun onDestroy() {
        presenter.cleanUp()
        super.onDestroy()
    }



    override fun onBackPressed() {
        //Nothing to do here
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, 100)
        } else {
            updateUI(account)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            Log.v("[API EXCEPTION]", "Sign in error", e)
        }
    }
}
