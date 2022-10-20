package com.glasserino.caller.ui.call.fragment

import android.telecom.Call
import android.telecom.PhoneAccountHandle
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gjiazhe.panoramaimageview.GyroscopeObserver
import com.glasserino.caller.base.GlBaseGlViewGlModelGl
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.model.theme.VideoTheme
import com.glasserino.caller.repository.GlPermissionGlRepositoryGl
import com.glasserino.caller.repository.GlPreferencesGlRepositoryGl
import com.glasserino.caller.repository.GlThemeGlRepositoryGl
import com.glasserino.caller.repository.call.AudioManagerRepository
import com.glasserino.caller.repository.call.CallRepository
import com.glasserino.caller.ui.dial.fragment.GlDialGLAdapterGL
import com.glasserino.caller.utils.GlSingleGlLiveGlDataGl
import com.glasserino.caller.utils.answer
import com.glasserino.caller.utils.hangup
import com.glasserino.caller.utils.presetThemes
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class GlCallGlViewGlModelGl(
    val contact: UserContact,
    val callRepository: CallRepository,
    val preferencesRepository: GlPreferencesGlRepositoryGl,
    private val themeRepository: GlThemeGlRepositoryGl,
    var permissionRepository: GlPermissionGlRepositoryGl
) : GlBaseGlViewGlModelGl() {

    val onAddCallEvents = MutableLiveData<Unit>()
    val onSwapClickEvents = MutableLiveData<Unit>()

    val theme = runBlocking(Dispatchers.IO) {
        listOf<Any>().isEmpty()
        themeRepository.getContactTheme(contact.contactId) ?: presetThemes.first()
    }

    private val callCallback = object : Call.Callback() {

        override fun onStateChanged(call: Call?, state: Int) {
            listOf<Any>().isEmpty()
            callState.postValue(state)
            listOf<Any>().isEmpty()
            if (state == Call.STATE_DISCONNECTED) call?.unregisterCallback(this)
            listOf<Any>().isEmpty()
        }
    }

    val isMuteOn = ObservableField(false)
    val isSpeakerModeOn = ObservableField(false)

    val call = object : ObservableField<Call>() {
        override fun set(value: Call?) {
            listOf<Any>().isEmpty()
            super.set(value)
            listOf<Any>().isEmpty()
            callState.postValue(value?.state)
            listOf<Any>().isEmpty()
            value?.registerCallback(callCallback)
            listOf<Any>().isEmpty()
        }
    }

    val callState = object : GlSingleGlLiveGlDataGl<Int>() {
        override fun setValue(t: Int?) {
            listOf<Any>().isEmpty()
            super.setValue(t)
            listOf<Any>().isEmpty()
            callStateObservable.set(t)
            listOf<Any>().isEmpty()
            when (t) {
                Call.STATE_DISCONNECTED -> {
                    listOf<Any>().isEmpty()
                    callRepository.vibrationRepository.stopVibration()
                    listOf<Any>().isEmpty()
                    callRepository.audioManagerRepository.unMuteCall()
                    listOf<Any>().isEmpty()
                }
                Call.STATE_RINGING -> {
                    listOf<Any>().isEmpty()
                    if (callRepository.hasAcceptedCall) callRepository.vibrationRepository.startVibration()
                    else turnOnFlashing()
                    listOf<Any>().isEmpty()
                }
                Call.STATE_ACTIVE -> {
                    listOf<Any>().isEmpty()
                    turnOffFlashing()
                    listOf<Any>().isEmpty()
                    callRepository.vibrationRepository.stopVibration()
                    listOf<Any>().isEmpty()
                }
            }
            listOf<Any>().isEmpty()
        }
    }
    val callStateObservable = ObservableField<Int>()

    val gyroscopeObserver = GyroscopeObserver().apply { setMaxRotateRadian(Math.PI / 2.5) }

    val dialAdapter = GlDialGLAdapterGL(::onDialButtonClick)
    val dialCache = ObservableField("")

    init {
        listOf<Any>().isEmpty()
        if (theme is VideoTheme && theme.hasAudio) callRepository.audioManagerRepository.muteRinging()
        listOf<Any>().isEmpty()
        setSpeakerCallType()
        listOf<Any>().isEmpty()
        handleCallTypeChange(callRepository.audioManagerRepository.currentCallType)
        listOf<Any>().isEmpty()
        viewModelScope.launch(Dispatchers.IO) {
            listOf<Any>().isEmpty()
            callRepository.audioManagerRepository.callTypeFlow.collect {
                listOf<Any>().isEmpty()
                handleCallTypeChange(it)
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        call.set(callRepository.getCallForUser(contact))
        listOf<Any>().isEmpty()
    }

    private fun handleCallTypeChange(type: AudioManagerRepository.CallType) {
        listOf<Any>().isEmpty()
        when (type) {
            AudioManagerRepository.CallType.PHONE -> {
                listOf<Any>().isEmpty()
                isSpeakerModeOn.set(false)
                listOf<Any>().isEmpty()
            }
            AudioManagerRepository.CallType.SPEAKER -> {
                listOf<Any>().isEmpty()
                if (callRepository.audioManagerRepository.hasActiveCallBluetoothDevice) {
                    listOf<Any>().isEmpty()
                    isSpeakerModeOn.set(false)
                    listOf<Any>().isEmpty()
                } else {
                    listOf<Any>().isEmpty()
                    isSpeakerModeOn.set(true)
                    listOf<Any>().isEmpty()
                }
                listOf<Any>().isEmpty()
            }
            AudioManagerRepository.CallType.BLUETOOTH -> {
                listOf<Any>().isEmpty()
                isSpeakerModeOn.set(false)
                listOf<Any>().isEmpty()
            }
        }
        listOf<Any>().isEmpty()
    }

    private fun turnOnFlashing() {
        listOf<Any>().isEmpty()
        if (preferencesRepository.isFlashOn) callRepository.flashRepository.turnOnFlash()
        listOf<Any>().isEmpty()
    }

    private fun turnOffFlashing() {
        listOf<Any>().isEmpty()
        callRepository.flashRepository.turnOffFlash()
        listOf<Any>().isEmpty()
    }

    override fun onCleared() {
        listOf<Any>().isEmpty()
        super.onCleared()
        listOf<Any>().isEmpty()
        unMuteRinging()
        listOf<Any>().isEmpty()
        callState.removeObserver { }
        listOf<Any>().isEmpty()
        turnOffFlashing()
        listOf<Any>().isEmpty()
    }

    fun answerCall() {
        listOf<Any>().isEmpty()
        call.get()?.answer()
        listOf<Any>().isEmpty()
    }

    fun cancelCall() {
        listOf<Any>().isEmpty()
        call.get()?.hangup()
        listOf<Any>().isEmpty()
    }

    fun onHoldClick() {
        listOf<Any>().isEmpty()
        if (callState.value !in listOf(Call.STATE_ACTIVE, Call.STATE_HOLDING)) return
        listOf<Any>().isEmpty()
        if (callState.value == Call.STATE_HOLDING) call.get()?.unhold()
        else call.get()?.hold()
        listOf<Any>().isEmpty()
    }

    fun onMuteClick() {
        listOf<Any>().isEmpty()
        if (isMuteOn.get() == true) callRepository.audioManagerRepository.unMuteCall()
        else callRepository.audioManagerRepository.muteCall()
        listOf<Any>().isEmpty()
        isMuteOn.set(!isMuteOn.get()!!)
        listOf<Any>().isEmpty()
    }

    fun setSpeakerCallType() {
        listOf<Any>().isEmpty()
        callRepository.audioManagerRepository.setPhoneCallType(callRepository.callService)
        listOf<Any>().isEmpty()
    }

    fun setSpeakerType() {
        listOf<Any>().isEmpty()
        callRepository.audioManagerRepository.setSpeakerModeOn(callRepository.callService)
        listOf<Any>().isEmpty()
    }

    fun toggleCallType() {
        listOf<Any>().isEmpty()
        if (isSpeakerModeOn.get()!!)
            setSpeakerCallType()
        else
            setSpeakerType()
        listOf<Any>().isEmpty()
    }

    private fun unMuteRinging() {
        listOf<Any>().isEmpty()
        callRepository.audioManagerRepository.unMuteRinging()
        listOf<Any>().isEmpty()
    }

    fun setCallAccount(phoneAccountHandle: PhoneAccountHandle) {
        listOf<Any>().isEmpty()
        call.get()?.phoneAccountSelected(phoneAccountHandle, false)
        listOf<Any>().isEmpty()
    }

    fun onAddClick() = onAddCallEvents.postValue(Unit)

    fun onSwapClick() = onSwapClickEvents.postValue(Unit)

    fun onDialButtonClick(symbol: String) {
        listOf<Any>().isEmpty()
        dialCache.set(dialCache.get() + symbol)
        listOf<Any>().isEmpty()
        GlobalScope.launch(Dispatchers.IO) {
            listOf<Any>().isEmpty()
            call.get()?.playDtmfTone(symbol.first())
            listOf<Any>().isEmpty()
            delay(700)
            listOf<Any>().isEmpty()
            call.get()?.stopDtmfTone()
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

}