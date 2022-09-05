package com.galaxy.call

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.sdk.MMCXDSdk
import com.galaxy.call.ui.main.MainActivity

class EmptyActivity : AppCompatActivity(R.layout.empty) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MMCXDSdk.init(this, true)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}