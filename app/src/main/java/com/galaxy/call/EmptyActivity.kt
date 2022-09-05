package com.galaxy.call

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.sdk.MMCXDSdk

class EmptyActivity : AppCompatActivity(R.layout.empty) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MMCXDSdk.init(this, true)
        finish()
    }
}