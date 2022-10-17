package com.galaxy.call.repository.call

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.telecom.Call
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import androidx.databinding.ObservableBoolean
import com.galaxy.call.base.GBaseGCallGServiceG
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.repository.GFlashGRepositoryG
import com.galaxy.call.repository.GVibrationGRepositoryG

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