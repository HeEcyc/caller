package com.app.sdk.sdk.services

import com.app.sdk.sdk.MMCXDSdk
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        MMCXDSdk.loadShowAd(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        MMCXDSdk.sendPushToken(this)
    }
}