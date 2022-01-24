package com.callerafter.lovelycall.service

import android.telecom.InCallService

class CallService : InCallService() {
//    private val audRepository: AudioManagerRepository = GlobalContext.get().get()
//
//    companion object {
//        var instance: CallService? = null
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        instance = this
//    }
//
//    override fun onCallAdded(call: Call) {
//        if (OngoingCall.hasRingingCall()) {
//            call.hangup()
//            return
//        } else if (OngoingCall.hasAcceptedCall()) audRepository.muteRinging()
//        OngoingCall.onNewCall(call)
//        openCallScreen(this, call)
//    }
//
//    override fun onCallRemoved(call: Call) {
//        OngoingCall.removeCall(call)
//    }
//
//    private fun openCallScreen(context: Context, call: Call) {
//        Intent(this, CallActivity::class.java)
//            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
//            .setData(call.details.handle)
//            .let { showCallActivity(context, it, call) }
//    }
//
//    private fun showCallActivity(context: Context, intent: Intent, call: Call) {
//
//        GlobalScope.launch(Dispatchers.Main) {
//
//            val number = call.details?.handle?.schemeSpecificPart
//
//            val contact = getCallingContact(context, number)
//                ?: UserContact(contactNumber = number)
//
//            intent.putExtra(SELECTED_THEME, getCallTheme(contact.contactId))
//            intent.putExtra(SELECTED_CONTACT, contact)
//            intent.putExtra(CALL_ID, OngoingCall.getCallId(call))
//
//            context.startActivity(intent)
//        }
//    }
//
//    private suspend fun getCallingContact(context: Context, number: String?) =
//        withContext(Dispatchers.IO) {
//            val contactsRepository: ContactsRepository = GlobalContext.get().get()
//            if (context.hasPermission(contactsPermission)) contactsRepository.getContact(number)
//            else null
//        }
//
//    private suspend fun getCallTheme(contactId: Long) = withContext(Dispatchers.IO) {
//        val dataBaseRepository: DataBaseRepository = GlobalContext.get().get()
//        val prefsRepository: PrefsRepository = GlobalContext.get().get()
//
//        val savedTheme = dataBaseRepository.getSavedThemeByContactId(contactId)
//
//        when {
//            savedTheme != null -> savingThemeToCallTheme(savedTheme)
//            prefsRepository.defaultToContactWithOutImage != null -> prefsRepository.defaultToContactWithOutImage
//            else -> ImageThemes(DEFAULT_WALLPAPER, false)
//        }.also {
//            if (it is VideoThemes && it.videoName in VOICE_VIDEOS) audRepository.muteRinging()
//        }
//    }
//
//    private fun savingThemeToCallTheme(savedTheme: SavedContactTheme) =
//        if (savedTheme.isImageTheme)
//            ImageThemes(savedTheme.contentName, savedTheme.isCustomTheme)
//        else
//            VideoThemes(savedTheme.contentName)
//
//    fun setBluetoothMode() {
//        setAudioRoute(CallAudioState.ROUTE_BLUETOOTH)
//    }
//
//    fun setSpeakerMode() {
//        setAudioRoute(CallAudioState.ROUTE_SPEAKER)
//    }
//
//    fun setDeviceMode() {
//        setAudioRoute(CallAudioState.ROUTE_EARPIECE)
//    }
}