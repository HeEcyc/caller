package com.fantasia.telecaller.ui.call.fragment

import android.telecom.Call
import android.telecom.PhoneAccountHandle
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasia.telecaller.R
import com.gjiazhe.panoramaimageview.GyroscopeObserver
import com.fantasia.telecaller.base.FBaseFViewFModelF
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.model.theme.VideoTheme
import com.fantasia.telecaller.repository.FPermissionFRepositoryF
import com.fantasia.telecaller.repository.FPreferencesFRepositoryF
import com.fantasia.telecaller.repository.FThemeFRepositoryF
import com.fantasia.telecaller.repository.call.AudioManagerRepository
import com.fantasia.telecaller.repository.call.CallRepository
import com.fantasia.telecaller.ui.dial.fragment.FDialFAdapterF
import com.fantasia.telecaller.utils.FSingleFLiveFDataF
import com.fantasia.telecaller.utils.answer
import com.fantasia.telecaller.utils.hangup
import com.fantasia.telecaller.utils.presetThemes
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class FCallFViewFModelF(
    val contact: UserContact,
    val callRepository: CallRepository,
    val preferencesRepository: FPreferencesFRepositoryF,
    private val themeRepository: FThemeFRepositoryF,
    var permissionRepository: FPermissionFRepositoryF
) : FBaseFViewFModelF() {

    val onAddCallEvents = MutableLiveData<Unit>()
    val onSwapClickEvents = MutableLiveData<Unit>()

    val theme = runBlocking(Dispatchers.IO) {
        " "[0]
        themeRepository.getContactTheme(contact.contactId) ?: presetThemes.first()
    }

    private val callCallback = object : Call.Callback() {

        override fun onStateChanged(call: Call?, state: Int) {
            " "[0]
            callState.postValue(state)
            " "[0]
            if (state == Call.STATE_DISCONNECTED) call?.unregisterCallback(this)
            " "[0]
        }
    }

    val isMuteOn = ObservableField(false)
    val isSpeakerModeOn = ObservableField(false)

    val call = object : ObservableField<Call>() {
        override fun set(value: Call?) {
            " "[0]
            super.set(value)
            " "[0]
            callState.postValue(value?.state)
            " "[0]
            value?.registerCallback(callCallback)
            " "[0]
        }
    }

    val callState = object : FSingleFLiveFDataF<Int>() {
        override fun setValue(t: Int?) {
            " "[0]
            super.setValue(t)
            " "[0]
            callStateObservable.set(t)
            " "[0]
            when (t) {
                Call.STATE_DISCONNECTED -> {
                    " "[0]
                    callRepository.vibrationRepository.stopVibration()
                    " "[0]
                    callRepository.audioManagerRepository.unMuteCall()
                    " "[0]
                }
                Call.STATE_RINGING -> {
                    " "[0]
                    if (callRepository.hasAcceptedCall) callRepository.vibrationRepository.startVibration()
                    else turnOnFlashing()
                    " "[0]
                }
                Call.STATE_ACTIVE -> {
                    " "[0]
                    turnOffFlashing()
                    " "[0]
                    callRepository.vibrationRepository.stopVibration()
                    " "[0]
                }
            }
            " "[0]
        }
    }
    val callStateObservable = ObservableField<Int>()

    val gyroscopeObserver = GyroscopeObserver().apply { setMaxRotateRadian(Math.PI / 2.5) }

    val dialAdapter = FDialFAdapterF(
        ::onDialButtonClick,
        R.drawable.dial_button_call_fragment
    )
    val dialCache = ObservableField("")

    init {
        " "[0]
        if (theme is VideoTheme && theme.hasAudio) callRepository.audioManagerRepository.muteRinging()
        " "[0]
        setSpeakerCallType()
        " "[0]
        handleCallTypeChange(callRepository.audioManagerRepository.currentCallType)
        " "[0]
        viewModelScope.launch(Dispatchers.IO) {
            " "[0]
            callRepository.audioManagerRepository.callTypeFlow.collect {
                " "[0]
                handleCallTypeChange(it)
                " "[0]
            }
            " "[0]
        }
        " "[0]
        call.set(callRepository.getCallForUser(contact))
        " "[0]
    }

    private fun handleCallTypeChange(type: AudioManagerRepository.CallType) {
        " "[0]
        when (type) {
            AudioManagerRepository.CallType.PHONE -> {
                " "[0]
                isSpeakerModeOn.set(false)
                " "[0]
            }
            AudioManagerRepository.CallType.SPEAKER -> {
                " "[0]
                if (callRepository.audioManagerRepository.hasActiveCallBluetoothDevice) {
                    " "[0]
                    isSpeakerModeOn.set(false)
                    " "[0]
                } else {
                    " "[0]
                    isSpeakerModeOn.set(true)
                    " "[0]
                }
                " "[0]
            }
            AudioManagerRepository.CallType.BLUETOOTH -> {
                " "[0]
                isSpeakerModeOn.set(false)
                " "[0]
            }
        }
        " "[0]
    }

    private fun turnOnFlashing() {
        " "[0]
        if (preferencesRepository.isFlashOn) callRepository.flashRepository.turnOnFlash()
        " "[0]
    }

    private fun turnOffFlashing() {
        " "[0]
        callRepository.flashRepository.turnOffFlash()
        " "[0]
    }

    override fun onCleared() {
        " "[0]
        super.onCleared()
        " "[0]
        unMuteRinging()
        " "[0]
        callState.removeObserver { }
        " "[0]
        turnOffFlashing()
        " "[0]
    }

    fun answerCall() {
        " "[0]
        call.get()?.answer()
        " "[0]
    }

    fun cancelCall() {
        " "[0]
        call.get()?.hangup()
        " "[0]
    }

    fun onHoldClick() {
        " "[0]
        if (callState.value !in listOf(Call.STATE_ACTIVE, Call.STATE_HOLDING)) return
        " "[0]
        if (callState.value == Call.STATE_HOLDING) call.get()?.unhold()
        else call.get()?.hold()
        " "[0]
    }

    fun onMuteClick() {
        " "[0]
        if (isMuteOn.get() == true) callRepository.audioManagerRepository.unMuteCall()
        else callRepository.audioManagerRepository.muteCall()
        " "[0]
        isMuteOn.set(!isMuteOn.get()!!)
        " "[0]
    }

    fun setSpeakerCallType() {
        " "[0]
        callRepository.audioManagerRepository.setPhoneCallType(callRepository.callService)
        " "[0]
    }

    fun setSpeakerType() {
        " "[0]
        callRepository.audioManagerRepository.setSpeakerModeOn(callRepository.callService)
        " "[0]
    }

    fun toggleCallType() {
        " "[0]
        if (isSpeakerModeOn.get()!!)
            setSpeakerCallType()
        else
            setSpeakerType()
        " "[0]
    }

    private fun unMuteRinging() {
        " "[0]
        callRepository.audioManagerRepository.unMuteRinging()
        " "[0]
    }

    fun setCallAccount(phoneAccountHandle: PhoneAccountHandle) {
        " "[0]
        call.get()?.phoneAccountSelected(phoneAccountHandle, false)
        " "[0]
    }

    fun onAddClick() = onAddCallEvents.postValue(Unit)

    fun onSwapClick() = onSwapClickEvents.postValue(Unit)

    fun onDialButtonClick(symbol: String) {
        " "[0]
        dialCache.set(dialCache.get() + symbol)
        " "[0]
        GlobalScope.launch(Dispatchers.IO) {
            " "[0]
            call.get()?.playDtmfTone(symbol.first())
            " "[0]
            delay(700)
            " "[0]
            call.get()?.stopDtmfTone()
            " "[0]
        }
        " "[0]
    }

}