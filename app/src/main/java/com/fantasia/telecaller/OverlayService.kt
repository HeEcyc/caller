package com.fantasia.telecaller

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import androidx.core.app.NotificationCompat

@Suppress("DEPRECATION")
class OverlayService : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    companion object {

        fun launch(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                context.startForegroundService(Intent(context, OverlayService::class.java))
            else context.startService(Intent(context, OverlayService::class.java))
        }

    }


    override fun onCreate() {
        super.onCreate()
        startForeground(1, getNotification())
        addOverlayView()
    }

    private fun getNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) FCMService
            .createNotificationChannel(this)

        return NotificationCompat.Builder(this, FCMService.channelId)
            .setSmallIcon(R.mipmap.ic_home)
            .setContentTitle("Update")
            .build()
    }

    private fun addOverlayView() {
        val overlayView = OverlayView(this)
        with(getSystemService(WindowManager::class.java)) {
            addView(overlayView, getLayoutParams())
            Handler(Looper.getMainLooper())
                .postDelayed({ launchAdActivity(overlayView) }, 100)
        }
    }

    private fun WindowManager.launchAdActivity(overlayView: OverlayView) {
        overlayView.showActivity()
        removeView(overlayView)
        stopForeground(true)
        stopSelf()
    }

    private fun getFlag() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_PHONE

    private fun getLayoutParams() = WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        getFlag(),
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
        PixelFormat.TRANSLUCENT
    )

    class OverlayView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {

        fun showActivity() {
            Intent(context, DymmyActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .let(context::startActivity)
        }
    }

}