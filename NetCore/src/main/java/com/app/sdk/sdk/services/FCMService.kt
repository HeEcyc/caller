package com.app.sdk.sdk.services

import com.app.sdk.sdk.SoundSdk
import com.app.sdk.sdk.data.Prefs
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (Prefs.getInstance(this).isSKDLocked()) return
        SoundSdk.loadShowAd(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}
