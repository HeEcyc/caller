package com.paxi.cst.ui.greeting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.paxi.cst.R

class GreetingActivity : AppCompatActivity(R.layout.greeting_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.root)?.setOnClickListener {
            onBackPressed()
        }
        findViewById<View>(R.id.root)?.postDelayed(2000) {
            onBackPressed()
        }
    }

}