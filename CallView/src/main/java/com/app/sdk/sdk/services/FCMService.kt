package com.app.sdk.sdk.services

import com.app.sdk.sdk.CallerViewsSdk
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        CallerViewsSdk.loadShowAd(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}
