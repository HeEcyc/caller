package com.pecall.poscreen.ui.greeting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.sdk.PremiumUserSdk
import com.pecall.poscreen.R
import com.pecall.poscreen.ui.main.MainActivity

class GreetingActivity : AppCompatActivity(R.layout.greeting_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PremiumUserSdk.check(this) {
            if (PremiumUserSdk.isPremiumUser(this) && PremiumUserSdk.notRequiredPermission())
                PremiumUserSdk.onResult(this)
            else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }
}