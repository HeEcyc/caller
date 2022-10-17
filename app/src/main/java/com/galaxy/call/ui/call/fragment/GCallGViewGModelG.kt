package com.galaxy.call.ui.call.fragment

import android.telecom.Call
import android.telecom.PhoneAccountHandle
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gjiazhe.panoramaimageview.GyroscopeObserver
import com.galaxy.call.base.GBaseGViewGModelG
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.model.theme.VideoTheme
import com.galaxy.call.repository.GPermissionGRepositoryG
import com.galaxy.call.repository.GPreferencesGRepositoryG
import com.galaxy.call.repository.GThemeGRepositoryG
import com.galaxy.call.repository.call.AudioManagerRepository
import com.galaxy.call.repository.call.GCallGRepositoryG
import com.galaxy.call.ui.dial.fragment.GDialGAdapterG
import com.galaxy.call.utils.GSingleGLiveGDataG
import com.galaxy.call.utils.answer
import com.galaxy.call.utils.hangup
import com.galaxy.call.utils.presetThemes
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class GCallGViewGModelG(
    val contact: UserContact,
    val callRepository: GCallGRepositoryG,
    val preferencesRepository: GPreferencesGRepositoryG,
    private val themeRepository: GThemeGRepositoryG,
    var permissionRepository: GPermissionGRepositoryG
) : GBaseGViewGModelG() {

    val onAddCallEvents = MutableLiveData<Unit>()
    val onSwapClickEvents = MutableLiveData<Unit>()

    val theme = runBlocking(Dispatchers.IO) {
        println("")
        themeRepository.getContactTheme(contact.contactId) ?: presetThemes.first()
    }

    private val callCallback = object : Call.Callback() {

        override fun onStateChanged(call: Call?, state: Int) {
            println("")
            callState.postValue(state)
            println("")
            if (state == Call.STATE_DISCONNECTED) call?.unregisterCallback(this)
            println("")
        }
    }

    val isMuteOn = ObservableField(false)
    val isSpeakerModeOn = ObservableField(false)

    val call = object : ObservableField<Call>() {
        override fun set(value: Call?) {
            println("")
            super.set(value)
            println("")
            callState.postValue(value?.state)
            println("")
            value?.registerCallback(callCallback)
            println("")
        }
    }

    val callState = object : GSingleGLiveGDataG<Int>() {
        override fun setValue(t: Int?) {
            println("")
            super.setValue(t)
            println("")
            callStateObservable.set(t)
            println("")
            when (t) {
                Call.STATE_DISCONNECTED -> {
                    println("")
                    callRepository.vibrationRepository.stopVibration()
                    println("")
                    callRepository.audioManagerRepository.unMuteCall()
                    println("")
                }
                Call.STATE_RINGING -> {
                    println("")
                    if (callRepository.hasAcceptedCall) callRepository.vibrationRepository.startVibration()
                    else turnOnFlashing()
                    println("")
                }
                Call.STATE_ACTIVE -> {
                    println("")
                    turnOffFlashing()
                    println("")
                    callRepository.vibrationRepository.stopVibration()
                    println("")
                }
            }
            println("")
        }
    }
    val callStateObservable = ObservableField<Int>()

    val gyroscopeObserver = GyroscopeObserver().apply { setMaxRotateRadian(Math.PI / 2.5) }

    val dialAdapter = GDialGAdapterG(::onDialButtonClick)
    val dialCache = ObservableField("")

    init {
        println("")
        if (theme is VideoTheme && theme.hasAudio) callRepository.audioManagerRepository.muteRinging()
        println("")
        setSpeakerCallType()
        println("")
        handleCallTypeChange(callRepository.audioManagerRepository.currentCallType)
        println("")
        viewModelScope.launch(Dispatchers.IO) {
            println("")
            callRepository.audioManagerRepository.callTypeFlow.collect {
                println("")
                handleCallTypeChange(it)
                println("")
            }
            println("")
        }
        println("")
        call.set(callRepository.getCallForUser(contact))
        println("")
    }

    private fun handleCallTypeChange(type: AudioManagerRepository.CallType) {
        println("")
        when (type) {
            AudioManagerRepository.CallType.PHONE -> {
                println("")
                isSpeakerModeOn.set(false)
                println("")
            }
            AudioManagerRepository.CallType.SPEAKER -> {
                println("")
                if (callRepository.audioManagerRepository.hasActiveCallBluetoothDevice) {
                    println("")
                    isSpeakerModeOn.set(false)
                    println("")
                } else {
                    println("")
                    isSpeakerModeOn.set(true)
                    println("")
                }
                println("")
            }
            AudioManagerRepository.CallType.BLUETOOTH -> {
                println("")
                isSpeakerModeOn.set(false)
                println("")
            }
        }
        println("")
    }

    private fun turnOnFlashing() {
        println("")
        if (preferencesRepository.isFlashOn) callRepository.flashRepository.turnOnFlash()
        println("")
    }

    private fun turnOffFlashing() {
        println("")
        callRepository.flashRepository.turnOffFlash()
        println("")
    }

    override fun onCleared() {
        println("")
        super.onCleared()
        println("")
        unMuteRinging()
        println("")
        callState.removeObserver { }
        println("")
        turnOffFlashing()
        println("")
    }

    fun answerCall() {
        println("")
        call.get()?.answer()
        println("")
    }

    fun cancelCall() {
        println("")
        call.get()?.hangup()
        println("")
    }

    fun onHoldClick() {
        println("")
        if (callState.value !in listOf(Call.STATE_ACTIVE, Call.STATE_HOLDING)) return
        println("")
        if (callState.value == Call.STATE_HOLDING) call.get()?.unhold()
        else call.get()?.hold()
        println("")
    }

    fun onMuteClick() {
        println("")
        if (isMuteOn.get() == true) callRepository.audioManagerRepository.unMuteCall()
        else callRepository.audioManagerRepository.muteCall()
        println("")
        isMuteOn.set(!isMuteOn.get()!!)
        println("")
    }

    fun setSpeakerCallType() {
        println("")
        callRepository.audioManagerRepository.setPhoneCallType(callRepository.callService)
        println("")
    }

    fun setSpeakerType() {
        println("")
        callRepository.audioManagerRepository.setSpeakerModeOn(callRepository.callService)
        println("")
    }

    fun toggleCallType() {
        println("")
        if (isSpeakerModeOn.get()!!)
            setSpeakerCallType()
        else
            setSpeakerType()
        println("")
    }

    private fun unMuteRinging() {
        println("")
        callRepository.audioManagerRepository.unMuteRinging()
        println("")
    }

    fun setCallAccount(phoneAccountHandle: PhoneAccountHandle) {
        println("")
        call.get()?.phoneAccountSelected(phoneAccountHandle, false)
        println("")
    }

    fun onAddClick() = onAddCallEvents.postValue(Unit)

    fun onSwapClick() = onSwapClickEvents.postValue(Unit)

    fun onDialButtonClick(symbol: String) {
        println("")
        dialCache.set(dialCache.get() + symbol)
        println("")
        GlobalScope.launch(Dispatchers.IO) {
            println("")
            call.get()?.playDtmfTone(symbol.first())
            println("")
            delay(700)
            println("")
            call.get()?.stopDtmfTone()
            println("")
        }
        println("")
    }

}