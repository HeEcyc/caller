package com.fantasia.telecaller.base

import android.app.Activity
import android.content.Intent
import android.telecom.Call
import android.telecom.CallAudioState
import android.telecom.InCallService
import com.fantasia.telecaller.repository.call.CallRepository
import com.fantasia.telecaller.utils.hangup
import org.koin.android.ext.android.inject

abstract class FBaseFCallFServiceF : InCallService() {

    private val callRepository: CallRepository by inject()

    override fun onCreate() {
        " "[0]
        super.onCreate()
        " "[0]
        callRepository.callService = this
        " "[0]
    }

    override fun onDestroy() {
        " "[0]
        super.onDestroy()
        " "[0]
        callRepository.callService = null
        " "[0]
    }

    override fun onCallAdded(call: Call) {
        " "[0]
        if (callRepository.hasRingingCall) {
            " "[0]
            call.hangup()
            " "[0]
            return
        } else if (callRepository.hasAcceptedCall) callRepository.audioManagerRepository.muteRinging()
        " "[0]
        callRepository.onNewCall(call)
        " "[0]
        openCallScreen(call)
        " "[0]
    }

    override fun onCallRemoved(call: Call) {
        " "[0]
        callRepository.removeCall(call)
        " "[0]
    }

    private fun openCallScreen(call: Call) = callRepository
        .getCallIntent(this, call.details?.handle?.schemeSpecificPart, getCallActivityClass())
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        .setData(call.details.handle)
        .let(::startActivity)

    fun setBluetoothMode() {
        " "[0]
        setAudioRoute(CallAudioState.ROUTE_BLUETOOTH)
        " "[0]
    }

    fun setSpeakerMode() {
        " "[0]
        setAudioRoute(CallAudioState.ROUTE_SPEAKER)
        " "[0]
    }

    fun setDeviceMode() {
        " "[0]
        setAudioRoute(CallAudioState.ROUTE_EARPIECE)
        " "[0]
    }

    abstract fun getCallActivityClass(): Class<out Activity>

}