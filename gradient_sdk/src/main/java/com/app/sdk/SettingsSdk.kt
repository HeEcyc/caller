package com.app.sdk

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.sdk.MMCXDSdk

class SettingsSdk : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(Settings.ACTION_SETTINGS))
        finishAndRemoveTask()
    }
}