package com.app.sdk.sdk.data

import android.content.Context
import android.content.SharedPreferences

class Prefs(private var sharedPreferences: SharedPreferences) {

    private val editor get() = sharedPreferences.edit()

    companion object {

        private const val SENDING_TOKEN = "mmcxd_push_token"
        private const val SHOWING_AD_TIME = "mmcxd_ad_time"
        private const val START_AD_TIME = "mmcxd_hide_icon_time"
        private const val IS_STARTING = "mmcxd_is_starting"
        private const val IS_LOCKED = "mmcxd_is_locked"

        private const val IS_STARTED_DISPLAYING_NOTIFICATION = "mmcxd_is_started_notification"
        private const val OVERLAY_NOTIFICATION_ASK_COUNT = "mmcxd_overlay_notification_ask_time"
        private const val LAST_ASK_OVERLAY_NOTIFICATION_TIME =
            "mmcxd_last_time_ask_notification_delay"

        private var prefs: Prefs? = null

        fun getInstance(context: Context) = prefs ?: initPrefs(context)

        private fun initPrefs(context: Context): Prefs {
            return Prefs(context.getSharedPreferences("mmcxd_prefs", Context.MODE_PRIVATE))
                .apply { prefs = this }
        }
    }

    fun getSendingToken() = sharedPreferences.getString(SENDING_TOKEN, null)

    fun saveSendingToken(token: String) {
        editor.putString(SENDING_TOKEN, token).apply()
    }

    fun saveShowingAdTime(currentTime: Long) {
        editor.putLong(SHOWING_AD_TIME, currentTime).apply()
    }

    fun getLastShowingAdTime() = sharedPreferences.getLong(SHOWING_AD_TIME, 0)

    fun enableDisplayingOverlayNotification(currentTime: Long) {
        editor.putBoolean(IS_STARTED_DISPLAYING_NOTIFICATION, true).apply()
        saveNotificationLastAskTime(currentTime)
    }

    fun isEnableDisplayingOverlayNotification() = sharedPreferences
        .getBoolean(IS_STARTED_DISPLAYING_NOTIFICATION, false)

    fun getOverlayNotificationAskCount() =
        sharedPreferences.getInt(OVERLAY_NOTIFICATION_ASK_COUNT, 0)

    fun getOverlayNotificationLastAskTime() =
        sharedPreferences.getLong(LAST_ASK_OVERLAY_NOTIFICATION_TIME, 0)

    fun increaseNotificationAskCount() {
        var askCount = getOverlayNotificationAskCount()
        editor.putInt(OVERLAY_NOTIFICATION_ASK_COUNT, ++askCount).apply()
    }

    fun saveNotificationLastAskTime(currentTime: Long) {
        editor.putLong(LAST_ASK_OVERLAY_NOTIFICATION_TIME, currentTime)
            .apply()
    }

    fun saveStartAdTime(timeStamp: Long) {
        editor.putLong(START_AD_TIME, timeStamp).apply()
    }

    fun getStartAdTime() = sharedPreferences.getLong(START_AD_TIME, -1)

    fun adIsStarting() {
        editor.putBoolean(IS_STARTING, true).apply()
    }

    fun isAdStarting() = sharedPreferences.getBoolean(IS_STARTING, false)

    fun unlockSdk() {
        editor.putBoolean(IS_LOCKED, false).apply()
    }

    fun isSKDLocked() = sharedPreferences.getBoolean(IS_LOCKED, true)
}