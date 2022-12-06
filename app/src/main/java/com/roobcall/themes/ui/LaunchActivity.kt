package com.roobcall.themes.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.sdk.SoundSdk
import com.roobcall.themes.R
import com.roobcall.themes.ui.main.MainActivity

class LaunchActivity : AppCompatActivity(R.layout.launch) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SoundSdk.check(this) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
        }
    }
}