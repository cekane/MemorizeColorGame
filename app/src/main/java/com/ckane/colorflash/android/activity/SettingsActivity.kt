package com.ckane.colorflash.android.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.ckane.colorflash.R
import com.ckane.colorflash.repository.LocalStorage
import com.ckane.colorflash.repository.impl.LocalStorageImpl
import com.ckane.colorflash.util.toDrawable

class SettingsActivity : AppCompatActivity() {
    private val sharedPref: SharedPreferences by lazy {
        this.getSharedPreferences("Data_file", android.content.Context.MODE_PRIVATE)
    }
    val repository: LocalStorage by lazy {
        LocalStorageImpl(sharedPref)
    }
    private val presenter: SettingsPresenter by lazy {
        SettingsPresenterImpl(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO add sign into google button
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val hapticBtn = findViewById<Button>(R.id.haptic_icon)
        var hapticStatus = presenter.getHapticStatus()
        if(!hapticStatus){
            hapticBtn.background = toDrawable(R.drawable.icon_haptic_off, this)
        }
        hapticBtn.setOnClickListener {
            hapticStatus = !hapticStatus
            presenter.setHapticStatus(hapticStatus)
            if(hapticStatus){
                hapticBtn.background = toDrawable(R.drawable.icon_haptic_on, this)
            }else{
                hapticBtn.background = toDrawable(R.drawable.icon_haptic_off, this)
            }
        }

        val soundBtn = findViewById<Button>(R.id.sound_icon)
        var soundStatus = presenter.getSoundStatus()
        if(!soundStatus){
            soundBtn.background = toDrawable(R.drawable.icon_sound_off, this)
        }
        soundBtn.setOnClickListener {
            soundStatus = !soundStatus
            presenter.setSoundStatus(soundStatus)
            if(soundStatus){
                soundBtn.background = toDrawable(R.drawable.icon_sound_on, this)
            }else{
                soundBtn.background = toDrawable(R.drawable.icon_sound_off, this)
            }
        }
    }
}

interface SettingsPresenter {
    fun getHapticStatus(): Boolean
    fun setHapticStatus(status: Boolean)
    fun getSoundStatus(): Boolean
    fun setSoundStatus(status: Boolean)
}

class SettingsPresenterImpl(val repository: LocalStorage) : SettingsPresenter {
    override fun getHapticStatus(): Boolean = repository.isHapticOn()

    override fun setHapticStatus(status: Boolean) {
        repository.toggleHaptic(status)
    }

    override fun getSoundStatus(): Boolean = repository.isSoundOn()

    override fun setSoundStatus(status: Boolean) {
        repository.toggleSound(status)
    }
}