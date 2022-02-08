package com.callerafter.lovelycall.ui.call.fragment

import android.telecom.Call
import android.telecom.PhoneAccountHandle
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseViewModel
import com.callerafter.lovelycall.model.contact.UserContact
import com.callerafter.lovelycall.repository.PreferencesRepository
import com.callerafter.lovelycall.repository.ThemeRepository
import com.callerafter.lovelycall.repository.call.AudioManagerRepository
import com.callerafter.lovelycall.repository.call.CallRepository
import com.callerafter.lovelycall.utils.SingleLiveData
import com.callerafter.lovelycall.utils.answer
import com.callerafter.lovelycall.utils.defaultTheme
import com.callerafter.lovelycall.utils.hangup
import com.gjiazhe.panoramaimageview.GyroscopeObserver
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class CallViewModel(
    val contact: UserContact,
    val callRepository: CallRepository,
    val preferencesRepository: PreferencesRepository,
    private val themeRepository: ThemeRepository
) : BaseViewModel() {

    val onAddCallEvents = MutableLiveData<Unit>()
    val onSwapClickEvents = MutableLiveData<Unit>()

    val theme = runBlocking(Dispatchers.IO) {
        themeRepository.getContactTheme(contact.contactId) ?: defaultTheme
    }

    val callIcon = ObservableField(R.drawable.ic_keyboard_speaker_default)

    private val callCallback = object : Call.Callback() {

        override fun onStateChanged(call: Call?, state: Int) {
            callState.postValue(state)
            if (state == Call.STATE_DISCONNECTED) call?.unregisterCallback(this)
        }
    }

    val isMuteOn = ObservableField(false)
    val isSpeakerModeOn = ObservableField(false)

    val call = object : ObservableField<Call>() {
        override fun set(value: Call?) {
            super.set(value)
            callState.postValue(value?.state)
            value?.registerCallback(callCallback)
        }
    }

    val callState = object : SingleLiveData<Int>() {
        override fun setValue(t: Int?) {
            super.setValue(t)
            callStateObservable.set(t)
            when (t) {
                Call.STATE_DISCONNECTED -> {
                    callRepository.vibrationRepository.stopVibration()
                    callRepository.audioManagerRepository.unMuteCall()
                }
                Call.STATE_RINGING -> {
                    if (callRepository.hasAcceptedCall) callRepository.vibrationRepository.startVibration()
                    else turnOnFlashing()
                }
                Call.STATE_ACTIVE -> {
                    turnOffFlashing()
                    callRepository.vibrationRepository.stopVibration()
                }
            }
        }
    }
    val callStateObservable = ObservableField<Int>()
    val onDialogOpenListener = SingleLiveData<AudioManagerRepository.CallType>()

    val gyroscopeObserver = GyroscopeObserver().apply { setMaxRotateRadian(Math.PI / 2.5) }

    init {
        handleCallTypeChange(callRepository.audioManagerRepository.currentCallType)
        viewModelScope.launch(Dispatchers.IO) {
            callRepository.audioManagerRepository.callTypeFlow.collect {
                handleCallTypeChange(it)
            }
        }
        call.set(callRepository.getCallForUser(contact))
    }

    private fun handleCallTypeChange(type: AudioManagerRepository.CallType) {
        when (type) {
            AudioManagerRepository.CallType.PHONE -> {
                callIcon.set(
                    if (callRepository.audioManagerRepository.hasActiveCallBluetoothDevice) R.drawable.ic_keyboard_speaker_phone
                    else R.drawable.ic_keyboard_speaker_default
                )
                isSpeakerModeOn.set(false)
            }
            AudioManagerRepository.CallType.SPEAKER -> {
                if (callRepository.audioManagerRepository.hasActiveCallBluetoothDevice) {
                    callIcon.set(R.drawable.ic_keyboard_speaker_loud)
                    isSpeakerModeOn.set(false)
                } else {
                    callIcon.set(R.drawable.ic_keyboard_speaker_default)
                    isSpeakerModeOn.set(true)
                }
            }
            AudioManagerRepository.CallType.BLUETOOTH -> {
                callIcon.set(R.drawable.ic_keyboard_speaker_bluetooth)
                isSpeakerModeOn.set(false)
            }
        }
    }

    private fun turnOnFlashing() {
        if (preferencesRepository.isFlashOn) callRepository.flashRepository.turnOnFlash()
    }

    private fun turnOffFlashing() {
        callRepository.flashRepository.turnOffFlash()
    }

    override fun onCleared() {
        super.onCleared()
        unMuteRinging()
        callState.removeObserver { }
        turnOffFlashing()
    }

    fun answerCall() {
        call.get()?.answer()
    }

    fun cancelCall() {
        call.get()?.hangup()
    }

    fun onHoldClick() {
        if (callState.value !in listOf(Call.STATE_ACTIVE, Call.STATE_HOLDING)) return
        if (callState.value == Call.STATE_HOLDING) call.get()?.unhold()
        else call.get()?.hold()
    }

    fun onMuteClick() {
        if (isMuteOn.get() == true) callRepository.audioManagerRepository.unMuteCall()
        else callRepository.audioManagerRepository.muteCall()
        isMuteOn.set(!isMuteOn.get()!!)
    }

    fun onSpeakerClick() {
        if (callRepository.audioManagerRepository.hasActiveCallBluetoothDevice) {
            onDialogOpenListener.postValue(callRepository.audioManagerRepository.currentCallType)
            return
        }
        if (isSpeakerModeOn.get() == true) callRepository.audioManagerRepository.setPhoneCallType(callRepository.callService)
        else callRepository.audioManagerRepository.setSpeakerModeOn(callRepository.callService)
        isSpeakerModeOn.set(!isSpeakerModeOn.get()!!)
    }

    fun setSpeakerCallType() {
        callRepository.audioManagerRepository.setPhoneCallType(callRepository.callService)
        callIcon.set(R.drawable.ic_keyboard_speaker_phone)
    }

    fun setBluetoothCallType() {
        callRepository.audioManagerRepository.setBluetoothCallType(callRepository.callService)
        callIcon.set(R.drawable.ic_keyboard_speaker_bluetooth)
    }

    fun setSpeakerType() {
        callRepository.audioManagerRepository.setSpeakerModeOn(callRepository.callService)
        callIcon.set(R.drawable.ic_keyboard_speaker_loud)
    }

    private fun unMuteRinging() {
        callRepository.audioManagerRepository.unMuteRinging()
    }

    fun setCallAccount(phoneAccountHandle: PhoneAccountHandle) {
        call.get()?.phoneAccountSelected(phoneAccountHandle, false)
    }

    fun onAddClick() = onAddCallEvents.postValue(Unit)

    fun onSwapClick() = onSwapClickEvents.postValue(Unit)

}