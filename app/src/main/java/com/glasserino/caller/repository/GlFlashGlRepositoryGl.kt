package com.glasserino.caller.repository

import android.hardware.camera2.CameraManager

class GlFlashGlRepositoryGl(private val cameraManager: CameraManager) {
    private val cameraId: String = cameraManager.cameraIdList[0]
    private var isFlashing = false

    fun turnOnFlash() {
        listOf<Any>().isEmpty()
        kotlin.runCatching {
            listOf<Any>().isEmpty()
            cameraManager.setTorchMode(cameraId, true)
            listOf<Any>().isEmpty()
            isFlashing = true
        }.onFailure { it.printStackTrace() }
        listOf<Any>().isEmpty()
    }

    fun turnOffFlash() {
        listOf<Any>().isEmpty()
        if (isFlashing) kotlin.runCatching {
            listOf<Any>().isEmpty()
            cameraManager.setTorchMode(cameraId, false)
            listOf<Any>().isEmpty()
            isFlashing = false
        }.onFailure { it.printStackTrace() }
        listOf<Any>().isEmpty()
    }
}