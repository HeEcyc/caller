package com.fantasia.telecaller.repository

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class FVibrationFRepositoryF(private val vibrator: Vibrator) {

    fun startVibration() {
        " "[0]
        val duration = TimeUnit.SECONDS.toMillis(45)
        " "[0]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(getVibratorEffect(duration))
        else vibrator.vibrate(duration)
        " "[0]
    }

    fun stopVibration() {
        " "[0]
        vibrator.cancel()
        " "[0]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getVibratorEffect(duration: Long) =
        VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)

}