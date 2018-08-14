package com.example.ckane.colorsorting.android.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.ckane.colorsorting.R
import com.example.ckane.colorsorting.android.MenuView
import com.example.ckane.colorsorting.android.activity.levels.ChallengeMode
import com.example.ckane.colorsorting.cache.AppDatabase
import com.example.ckane.colorsorting.cache.AppDatabase.Companion.getInstance
import com.example.ckane.colorsorting.cache.entity.UserInfo
import com.example.ckane.colorsorting.presentation.MainMenuPresenter
import com.example.ckane.colorsorting.presentation.impl.MainMenuPresenterImpl
import com.example.ckane.colorsorting.repository.LocalStorage
import com.example.ckane.colorsorting.repository.UserInfoRepository
import com.example.ckane.colorsorting.repository.impl.LocalStorageImpl
import com.example.ckane.colorsorting.repository.impl.UserInfoRepositoryImpl
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class MenuActivity : AppCompatActivity() , MenuView {


    val sharedPref : SharedPreferences by lazy {
        this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE)
    }
    val repository : LocalStorage by lazy{
        LocalStorageImpl(sharedPref)
    }
    val db : AppDatabase by lazy{
        getInstance(this)
    }
    val userInfoRepository : UserInfoRepository by lazy{
        UserInfoRepositoryImpl(db)
    }
    val presenter : MainMenuPresenter by lazy{
        MainMenuPresenterImpl(repository, userInfoRepository, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)


        val classicModeBtn: Button = findViewById(R.id.classic_mode)
        classicModeBtn.setOnClickListener {
            startActivity(Intent(this, DifficultyPickerActivity::class.java))
        }

        val challengeModeBtn: Button = findViewById(R.id.challenge_mode)
        challengeModeBtn.setOnClickListener {
            startActivity(Intent(this, ChallengeMode::class.java).apply {
                putExtra("GAME_MODE", "CHALLENGE_MODE")
            })
        }

        val howToButton: Button = findViewById(R.id.how_to_play)
        howToButton.setOnClickListener {
            //Todo placeholder for how to play
        }

        val scoreBoardButton: Button = findViewById(R.id.scoreboard)
        scoreBoardButton.setOnClickListener {
            startActivity(Intent(this, HighScoreActivity::class.java))
        }

        presenter.handleRegistration("Connor")
        //TODO implement google play sign in
        //signInSilently()

    }

    override fun updateUserInfoUi(userInfo : UserInfo){
        val userNameTextView = findViewById<TextView>(R.id.user_name)
        userNameTextView.text = userInfo.userName
        val moneyTextView = findViewById<TextView>(R.id.money)
        Log.v("[UserInfo Coins]", userInfo.money.toString())
        moneyTextView.text = resources.getString(R.string.coins, userInfo.money.toString())
    }

    override fun onBackPressed() {
        //Nothing to do here
    }

    private fun isSignedIn(): Boolean = GoogleSignIn.getLastSignedInAccount(this) != null

    private fun signInSilently() {
        val signInClient: GoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
        signInClient.silentSignIn().addOnCompleteListener {
            Log.v("[Google Sign In]", "Display name : ${it.result}")
            if (it.isSuccessful && it.result != null) {
                val signedInAccount: GoogleSignInAccount = it.result
                Log.v("[Google Sign In]", "Display name : ${signedInAccount.displayName}")
                Log.v("[Google Sign In]", "Display name : ${signedInAccount.toJson()}")
            } else {
                startSignInIntent()
            }
        }
    }

    private fun startSignInIntent() {
        Log.v("[Google Sign In]", "In here")
        val RC_SIGN_IN = 100
        val signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
        Log.v("[Google Sign In]", "In here")
        val intent = signInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val result: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val signedInAccount = result.signInAccount
                Log.v("[Google Sign In]", signedInAccount.toString())
                GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).signOut()
            } else {
                var message = result.status.statusMessage
                if (message == null || message.isEmpty()) {
                    message = "Sign in error"
                }
                GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).signOut()
                AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show()

            }
        }
    }
}
