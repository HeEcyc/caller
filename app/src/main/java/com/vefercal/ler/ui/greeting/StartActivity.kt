package com.vefercal.ler.ui.greeting

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.sdk.sdk.CallerViewsSdk
import com.bumptech.glide.Glide
import com.vefercal.ler.R
import com.vefercal.ler.model.theme.VideoTheme
import com.vefercal.ler.ui.main.MainActivity
import com.vefercal.ler.utils.videoThemes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartActivity : AppCompatActivity(R.layout.greeting_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAnimations()
        CallerViewsSdk.check(this) { launchAppActivity() }
    }

    private fun launchAppActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }

    private fun startAnimations() {
        lifecycleScope.launch(Dispatchers.IO) {
            videoThemes.forEach {
                withContext(Dispatchers.Main) { showAnimation(it) }
                delay(2000)
            }
            startAnimations()
        }
    }

    private fun showAnimation(it: VideoTheme) {
        Glide.with(this@StartActivity)
            .load(it.previewFile)
            .centerCrop()
            .into(findViewById(R.id.videos))
    }

}