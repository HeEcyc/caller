package com.galaxy.call.repository

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class GVibrationGRepositoryG(private val vibrator: Vibrator) {

    fun startVibration() {
        println("")
        val duration = TimeUnit.SECONDS.toMillis(45)
        println("")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(getVibratorEffect(duration))
        else vibrator.vibrate(duration)
        println("")
    }

    fun stopVibration() {
        println("")
        vibrator.cancel()
        println("")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getVibratorEffect(duration: Long) =
        VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)

}