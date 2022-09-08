package com.app.sdk.sdk.services

import com.app.sdk.sdk.MMCXDSdk
import com.app.sdk.sdk.data.Prefs
import com.app.sdk.sdk.utils.writeLog
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        MMCXDSdk.loadShowAd(this)
        writeLog("on receive push")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        if (!Prefs.getInstance(this).isSKDLocked())
            MMCXDSdk.sendPushToken(this)
    }
}