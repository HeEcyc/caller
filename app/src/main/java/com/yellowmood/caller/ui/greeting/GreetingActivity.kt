package com.yellowmood.caller.ui.greeting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yellowmood.caller.R

class GreetingActivity : AppCompatActivity(R.layout.greeting_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.button)?.setOnClickListener {
            onBackPressed()
        }
    }

}