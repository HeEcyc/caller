package com.vefercal.ler.ui.call.fragment

import android.graphics.Color
import android.telecom.Call
import android.telecom.PhoneAccountHandle
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vefercal.ler.R
import com.gjiazhe.panoramaimageview.GyroscopeObserver
import com.vefercal.ler.base.BaseViewModel
import com.vefercal.ler.model.contact.UserContact
import com.vefercal.ler.model.theme.VideoTheme
import com.vefercal.ler.repository.PermissionRepository
import com.vefercal.ler.repository.PreferencesRepository
import com.vefercal.ler.repository.ThemeRepository
import com.vefercal.ler.repository.call.AudioManagerRepository
import com.vefercal.ler.repository.call.CallRepository
import com.vefercal.ler.ui.dial.fragment.DialAdapter
import com.vefercal.ler.utils.SingleLiveData
import com.vefercal.ler.utils.answer
import com.vefercal.ler.utils.hangup
import com.vefercal.ler.utils.presetThemes
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class CallViewModel(
    val contact: UserContact,
    val callRepository: CallRepository,
    val preferencesRepository: PreferencesRepository,
    private val themeRepository: ThemeRepository,
    var permissionRepository: PermissionRepository
) : BaseViewModel() {

    val onAddCallEvents = MutableLiveData<Unit>()
    val onSwapClickEvents = MutableLiveData<Unit>()

    val theme = runBlocking(Dispatchers.IO) {
        themeRepository.getContactTheme(contact.contactId) ?: presetThemes.first()
    }

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

    val gyroscopeObserver = GyroscopeObserver().apply { setMaxRotateRadian(Math.PI / 2.5) }

    val dialAdapter = DialAdapter(
        ::onDialButtonClick,
        R.drawable.dial_button_call_fragment,
        Color.parseColor("#FFFFFF")
    )
    val dialCache = ObservableField("")

    init {
        if (theme is VideoTheme && theme.hasAudio) callRepository.audioManagerRepository.muteRinging()
        setSpeakerCallType()
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
                isSpeakerModeOn.set(false)
            }
            AudioManagerRepository.CallType.SPEAKER -> {
                if (callRepository.audioManagerRepository.hasActiveCallBluetoothDevice) {
                    isSpeakerModeOn.set(false)
                } else {
                    isSpeakerModeOn.set(true)
                }
            }
            AudioManagerRepository.CallType.BLUETOOTH -> {
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

    private fun setSpeakerCallType() {
        callRepository.audioManagerRepository.setPhoneCallType(callRepository.callService)
    }

    private fun setSpeakerType() {
        callRepository.audioManagerRepository.setSpeakerModeOn(callRepository.callService)
    }

    fun toggleCallType() {
        if (isSpeakerModeOn.get()!!)
            setSpeakerCallType()
        else
            setSpeakerType()
    }

    private fun unMuteRinging() {
        callRepository.audioManagerRepository.unMuteRinging()
    }

    fun setCallAccount(phoneAccountHandle: PhoneAccountHandle) {
        call.get()?.phoneAccountSelected(phoneAccountHandle, false)
    }

    fun onAddClick() = onAddCallEvents.postValue(Unit)

    fun onSwapClick() = onSwapClickEvents.postValue(Unit)

    private fun onDialButtonClick(symbol: String) {
        dialCache.set(dialCache.get() + symbol)
        GlobalScope.launch(Dispatchers.IO) {
            call.get()?.playDtmfTone(symbol.first())
            delay(700)
            call.get()?.stopDtmfTone()
        }
    }

}