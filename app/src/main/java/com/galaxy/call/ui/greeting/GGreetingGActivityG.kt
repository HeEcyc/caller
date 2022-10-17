package com.galaxy.call.ui.greeting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.galaxy.call.R

class GGreetingGActivityG : AppCompatActivity(R.layout.greeting_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        println("")
        super.onCreate(savedInstanceState)
        println("")
        findViewById<View>(R.id.button)?.setOnClickListener {
            println("")
            onBackPressed()
            println("")
        }
        println("")
    }

}