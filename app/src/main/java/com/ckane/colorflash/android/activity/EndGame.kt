package com.ckane.colorflash.android.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.ckane.colorflash.R
import com.ckane.colorflash.android.activity.levels.ChallengeMode
import com.ckane.colorflash.cache.AppDatabase
import com.ckane.colorflash.presentation.EndGamePresenter
import com.ckane.colorflash.presentation.impl.EndGamePresenterImpl
import com.ckane.colorflash.repository.LocalStorage
import com.ckane.colorflash.repository.UserInfoRepository
import com.ckane.colorflash.repository.impl.LocalStorageImpl
import com.ckane.colorflash.repository.impl.UserInfoRepositoryImpl
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.games.Games


class EndGame : AppCompatActivity() {

    private val sharedPref: SharedPreferences by lazy {
        this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE)
    }
    private val repository: LocalStorage by lazy {
        LocalStorageImpl(sharedPref)
    }
    val db: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }
    private val userInfoRepository: UserInfoRepository by lazy {
        UserInfoRepositoryImpl(db)
    }
    private val presenter: EndGamePresenter by lazy {
        EndGamePresenterImpl(repository, userInfoRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_game_activity)

        val score = intent.getIntExtra("FINAL_SCORE", 0)
        val mode = intent.getStringExtra("GAME_MODE")
        val coins = presenter.determineCoins(score, mode)

        presenter.updateCoins(presenter.getUserName(), coins)
        val scoreBoardKey = when(mode){
            getString(R.string.difficulty_easy) -> getString(R.string.scoreboard_easy_mode)
            getString(R.string.difficulty_hard) ->getString(R.string.scoreboard_hard_mode)
            getString(R.string.difficulty_challenge) -> getString(R.string.scoreboard_challenge_mode)
            else -> ""
        }

        postScore(score, scoreBoardKey)
        presenter.updateHighScore(mode, score)
//        presenter.updateHighScoreDatabase(score, AppDatabase.getInstance(this))

        val coinsTextView = findViewById<TextView>(R.id.coins)
        coinsTextView.text = resources.getString(R.string.end_game_coins, coins.toString())

        val scoreTextView = findViewById<TextView>(R.id.score)
        scoreTextView.text = score.toString()

        val tryAgainBtn: Button = findViewById(R.id.try_again)
        tryAgainBtn.setOnClickListener {
            startActivity(Intent(this, ChallengeMode::class.java).apply {
                putExtra("GAME_MODE", mode)
            })
        }

        val menuButton: Button = findViewById(R.id.menu)
        menuButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        val leaderBoardBtn : Button = findViewById(R.id.scoreboard_btn)
        leaderBoardBtn.setOnClickListener {
            val account = GoogleSignIn.getLastSignedInAccount(this)
            account?.let{noNullAccount ->
                Games.getLeaderboardsClient(this, noNullAccount)
                        .getLeaderboardIntent(scoreBoardKey)
                        .addOnSuccessListener { intent ->
                            startActivityForResult(intent, 9004)
                        }
            }
        }
    }

    private fun postScore(score: Int, mode: String){
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let{
            Games.getLeaderboardsClient(this, it).submitScore(mode, score.toLong())
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MenuActivity::class.java))
    }
}

