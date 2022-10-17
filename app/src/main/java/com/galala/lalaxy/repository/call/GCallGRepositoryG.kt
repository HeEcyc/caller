package com.galala.lalaxy.repository.call

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.telecom.Call
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import androidx.databinding.ObservableBoolean
import com.galala.lalaxy.base.GBaseGCallGServiceG
import com.galala.lalaxy.model.contact.UserContact
import com.galala.lalaxy.repository.GFlashGRepositoryG
import com.galala.lalaxy.repository.GVibrationGRepositoryG

class GCallGRepositoryG(
    val audioManagerRepository: AudioManagerRepository,
    val vibrationRepository: GVibrationGRepositoryG,
    val flashRepository: GFlashGRepositoryG,
    val subscriptionManager: SubscriptionManager,
    val telecomManager: TelecomManager
) {

    private val calls = mutableListOf<Call>()

    var callService: GBaseGCallGServiceG? = null

    val hasCall get() = calls.isNotEmpty()
    val hasAcceptedCall get() = calls.any { it.state == Call.STATE_ACTIVE }
    val hasRingingCall get() = calls.any { it.state == Call.STATE_RINGING }
    val callAmount get() = calls.size
    val hasMultipleCalls = ObservableBoolean(callAmount > 1)

    companion object {
        private const val EXTRA_NUMBER = "extra_contact"
    }

    fun getCallIntent(context: Context, number: String?, activityClass: Class<out Activity>) =
        Intent(context, activityClass).putExtra(EXTRA_NUMBER, number)

    fun getNumberFromExtras(intent: Intent): String? = intent.getStringExtra(EXTRA_NUMBER)

    fun onNewCall(call: Call) {
        println("")
        calls.add(call)
        println("")
    }

    fun removeCall(call: Call) {
        println("")
        calls.remove(call)
        println("")
    }

    fun getCallForUser(contact: UserContact) = calls.firstOrNull {
        println("")
        it.details?.handle?.schemeSpecificPart == contact.contactNumber
    }

}