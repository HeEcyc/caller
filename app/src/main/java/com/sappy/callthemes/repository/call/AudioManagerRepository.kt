package com.sappy.callthemes.repository.call

import android.media.AudioDeviceInfo
import android.media.AudioManager
import com.sappy.callthemes.base.BaseCallService
import com.sappy.callthemes.repository.call.AudioManagerRepository.CallType.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AudioManagerRepository(private val audioManager: AudioManager) {

    val callTypeFlow = MutableSharedFlow<CallType>()

    val currentCallType: CallType
        get() = when {
            audioManager.isBluetoothScoOn -> BLUETOOTH
            audioManager.isSpeakerphoneOn -> SPEAKER
            else -> PHONE

        }
    private val listHeadSetDevices = listOf(AudioDeviceInfo.TYPE_BLUETOOTH_SCO)

    val hasActiveCallBluetoothDevice: Boolean
        get() {
            audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS).forEach {
                if (it.type in listHeadSetDevices) return true
            }
            return false
        }

    fun muteCall() {
        audioManager.isMicrophoneMute = true
    }

    fun unMuteCall() {
        audioManager.isMicrophoneMute = false
    }

    fun setPhoneCallType(callService: BaseCallService?) {
        audioManager.isBluetoothScoOn = false
        audioManager.isSpeakerphoneOn = false
        callService?.setDeviceMode()
        onCallTypeChanged(PHONE)
    }

    fun setBluetoothCallType(callService: BaseCallService?) {
        audioManager.isBluetoothScoOn = true
        audioManager.isSpeakerphoneOn = false
        callService?.setBluetoothMode()
        onCallTypeChanged(BLUETOOTH)
    }

    fun setSpeakerModeOn(callService: BaseCallService?) {
        callService?.setSpeakerMode()
        audioManager.isSpeakerphoneOn = true
        audioManager.isBluetoothScoOn = false
        onCallTypeChanged(SPEAKER)
    }

    private fun onCallTypeChanged(type: CallType) =
        GlobalScope.launch(Dispatchers.IO) { callTypeFlow.emit(type) }

    fun muteRinging() {
        kotlin.runCatching {
            audioManager.adjustStreamVolume(
                AudioManager.STREAM_NOTIFICATION,
                AudioManager.ADJUST_MUTE,
                0
            )
            audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0)
        }
    }

    fun unMuteRinging() {
        kotlin.runCatching {
            audioManager.adjustStreamVolume(
                AudioManager.STREAM_NOTIFICATION,
                AudioManager.ADJUST_UNMUTE,
                0
            )
            audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0)
        }
    }

    enum class CallType {
        BLUETOOTH,
        PHONE,
        SPEAKER
    }

}