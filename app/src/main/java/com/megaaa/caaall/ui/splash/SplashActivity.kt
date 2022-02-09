package com.megaaa.caaall.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseActivity
import com.megaaa.caaall.databinding.SplashActivityBinding
import com.megaaa.caaall.ui.main.MainActivity
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