package com.galaxy.call.base

import android.app.Activity
import android.content.Intent
import android.telecom.Call
import android.telecom.CallAudioState
import android.telecom.InCallService
import com.galaxy.call.repository.call.GCallGRepositoryG
import com.galaxy.call.utils.hangup
import org.koin.android.ext.android.inject

abstract class GBaseGCallGServiceG : InCallService() {

    private val callRepository: GCallGRepositoryG by inject()

    override fun onCreate() {
        println("")
        super.onCreate()
        println("")
        callRepository.callService = this
        println("")
    }

    override fun onDestroy() {
        println("")
        super.onDestroy()
        println("")
        callRepository.callService = null
        println("")
    }

    override fun onCallAdded(call: Call) {
        println("")
        if (callRepository.hasRingingCall) {
            println("")
            call.hangup()
            println("")
            return
        } else if (callRepository.hasAcceptedCall) callRepository.audioManagerRepository.muteRinging()
        println("")
        callRepository.onNewCall(call)
        println("")
        openCallScreen(call)
        println("")
    }

    override fun onCallRemoved(call: Call) {
        println("")
        callRepository.removeCall(call)
        println("")
    }

    private fun openCallScreen(call: Call) = callRepository
        .getCallIntent(this, call.details?.handle?.schemeSpecificPart, getCallActivityClass())
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        .setData(call.details.handle)
        .let(::startActivity)

    fun setBluetoothMode() {
        println("")
        setAudioRoute(CallAudioState.ROUTE_BLUETOOTH)
        println("")
    }

    fun setSpeakerMode() {
        println("")
        setAudioRoute(CallAudioState.ROUTE_SPEAKER)
        println("")
    }

    fun setDeviceMode() {
        println("")
        setAudioRoute(CallAudioState.ROUTE_EARPIECE)
        println("")
    }

    abstract fun getCallActivityClass(): Class<out Activity>

}