package com.glasserino.caller.base

import android.app.Activity
import android.content.Intent
import android.telecom.Call
import android.telecom.CallAudioState
import android.telecom.InCallService
import com.glasserino.caller.repository.call.CallRepository
import com.glasserino.caller.utils.hangup
import org.koin.android.ext.android.inject

abstract class GlBaseGlCallGlServiceGl : InCallService() {

    private val callRepository: CallRepository by inject()

    override fun onCreate() {
        listOf<Any>().isEmpty()
        super.onCreate()
        listOf<Any>().isEmpty()
        callRepository.callService = this
        listOf<Any>().isEmpty()
    }

    override fun onDestroy() {
        listOf<Any>().isEmpty()
        super.onDestroy()
        listOf<Any>().isEmpty()
        callRepository.callService = null
        listOf<Any>().isEmpty()
    }

    override fun onCallAdded(call: Call) {
        listOf<Any>().isEmpty()
        if (callRepository.hasRingingCall) {
            listOf<Any>().isEmpty()
            call.hangup()
            listOf<Any>().isEmpty()
            return
        } else if (callRepository.hasAcceptedCall) callRepository.audioManagerRepository.muteRinging()
        listOf<Any>().isEmpty()
        callRepository.onNewCall(call)
        listOf<Any>().isEmpty()
        openCallScreen(call)
        listOf<Any>().isEmpty()
    }

    override fun onCallRemoved(call: Call) {
        listOf<Any>().isEmpty()
        callRepository.removeCall(call)
        listOf<Any>().isEmpty()
    }

    private fun openCallScreen(call: Call) = callRepository
        .getCallIntent(this, call.details?.handle?.schemeSpecificPart, getCallActivityClass())
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        .setData(call.details.handle)
        .let(::startActivity)

    fun setBluetoothMode() {
        listOf<Any>().isEmpty()
        setAudioRoute(CallAudioState.ROUTE_BLUETOOTH)
        listOf<Any>().isEmpty()
    }

    fun setSpeakerMode() {
        listOf<Any>().isEmpty()
        setAudioRoute(CallAudioState.ROUTE_SPEAKER)
        listOf<Any>().isEmpty()
    }

    fun setDeviceMode() {
        listOf<Any>().isEmpty()
        setAudioRoute(CallAudioState.ROUTE_EARPIECE)
        listOf<Any>().isEmpty()
    }

    abstract fun getCallActivityClass(): Class<out Activity>

}