package com.bbbotttixxx.callscreen.base

import android.app.Activity
import android.content.Intent
import android.telecom.Call
import android.telecom.CallAudioState
import android.telecom.InCallService
import com.bbbotttixxx.callscreen.repository.call.CallRepository
import com.bbbotttixxx.callscreen.utils.hangup
import org.koin.android.ext.android.inject

abstract class BaseCallService : InCallService() {

    private val callRepository: CallRepository by inject()

    override fun onCreate() {
        super.onCreate()
        callRepository.callService = this
    }

    override fun onDestroy() {
        super.onDestroy()
        callRepository.callService = null
    }

    override fun onCallAdded(call: Call) {
        if (callRepository.hasRingingCall) {
            call.hangup()
            return
        } else if (callRepository.hasAcceptedCall) callRepository.audioManagerRepository.muteRinging()
        callRepository.onNewCall(call)
        openCallScreen(call)
    }

    override fun onCallRemoved(call: Call) {
        callRepository.removeCall(call)
    }

    private fun openCallScreen(call: Call) = callRepository
        .getCallIntent(this, call.details?.handle?.schemeSpecificPart, getCallActivityClass())
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        .setData(call.details.handle)
        .let(::startActivity)

    fun setSpeakerMode() {
        setAudioRoute(CallAudioState.ROUTE_SPEAKER)
    }

    fun setDeviceMode() {
        setAudioRoute(CallAudioState.ROUTE_EARPIECE)
    }

    abstract fun getCallActivityClass(): Class<out Activity>

}