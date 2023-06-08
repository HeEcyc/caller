package com.yee.zer.repository

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class VibrationRepository(private val vibrator: Vibrator) {

    fun startVibration() {
        val duration = TimeUnit.SECONDS.toMillis(45)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(getVibratorEffect(duration))
        else vibrator.vibrate(duration)
    }

    fun stopVibration() {
        vibrator.cancel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getVibratorEffect(duration: Long) =
        VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)

}