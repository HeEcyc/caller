package com.callerafter.lovelycall.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseActivity
import com.callerafter.lovelycall.databinding.SplashActivityBinding
import com.callerafter.lovelycall.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<SplashViewModel, SplashActivityBinding>() {

    private val viewModel: SplashViewModel by viewModel()

    override fun provideLayoutId(): Int = R.layout.splash_activity

    override fun setupUI() {
        Handler(Looper.getMainLooper()).postDelayed(3000) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun provideViewModel(): SplashViewModel = viewModel

    override fun onBackPressed() {}

}