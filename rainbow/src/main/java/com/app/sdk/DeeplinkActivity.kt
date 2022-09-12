package com.app.sdk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.sdk.SonataSdk
import com.app.sdk.sdk.utils.writeLog


class DeeplinkActivity : AppCompatActivity(R.layout.empty) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SonataSdk.init(this)
        packageManager
            .getLaunchIntentForPackage(packageName)
            .let(::startActivity)
        finish()
    }
}