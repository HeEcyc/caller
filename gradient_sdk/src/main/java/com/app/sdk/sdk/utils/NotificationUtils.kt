package com.app.sdk.sdk.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.app.sdk.PermissionActivity
import com.app.sdk.R

object NotificationUtils {

    private const val channelId = "channel"

    fun getServiceNotification(context: Context): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel(context)

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("-")
            .build()
    }

    fun getOverlayNotification(context: Context): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel(context)

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(context.applicationInfo.icon)
            .setContentTitle(getAppName(context))
            .setAutoCancel(true)
            .setContentIntent(getOverlayPendingIntent(context))
            .setContentText(context.getString(R.string.give_permission_text))
            .build()
    }

    private fun getAppName(context: Context) = context.packageManager
        .getApplicationLabel(context.applicationInfo).toString()

    private fun getOverlayPendingIntent(context: Context): PendingIntent? {
        val intent = Intent(context, PermissionActivity::class.java)
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    fun showNotification(context: Context, notification: Notification) {
        context.getSystemService(NotificationManager::class.java)
            .notify(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) {
        context.getSystemService(NotificationManager::class.java)
            .takeIf { it.getNotificationChannel(channelId) == null }
            ?.createNotificationChannel(getChanel())
            ?: return
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getChanel() =
        NotificationChannel(channelId, "Call", NotificationManager.IMPORTANCE_DEFAULT)
}