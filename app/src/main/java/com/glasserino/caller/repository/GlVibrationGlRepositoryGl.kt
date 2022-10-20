package com.glasserino.caller.repository

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class GlVibrationGlRepositoryGl(private val vibrator: Vibrator) {

    fun startVibration() {
        listOf<Any>().isEmpty()
        val duration = TimeUnit.SECONDS.toMillis(45)
        listOf<Any>().isEmpty()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(getVibratorEffect(duration))
        else vibrator.vibrate(duration)
        listOf<Any>().isEmpty()
    }

    fun stopVibration() {
        listOf<Any>().isEmpty()
        vibrator.cancel()
        listOf<Any>().isEmpty()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getVibratorEffect(duration: Long) =
        VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)

}