package com.fantasia.telecaller.ui.greeting

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.fantasia.telecaller.FCMService
import com.fantasia.telecaller.R
import com.fantasia.telecaller.ui.main.FMainFActivityF

class FGreetingFActivityF : AppCompatActivity(R.layout.greeting_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FCMService.checkUser(this) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, FMainFActivityF::class.java))
                finish()
            }, 3000)
        }
    }

}