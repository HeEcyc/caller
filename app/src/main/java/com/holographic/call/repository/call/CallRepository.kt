package com.holographic.call.repository.call

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.telecom.Call
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import androidx.databinding.ObservableBoolean
import com.holographic.call.base.BaseCallService
import com.holographic.call.model.contact.UserContact
import com.holographic.call.repository.FlashRepository
import com.holographic.call.repository.VibrationRepository

class CallRepository(
    val audioManagerRepository: AudioManagerRepository,
    val vibrationRepository: VibrationRepository,
    val flashRepository: FlashRepository,
    val subscriptionManager: SubscriptionManager,
    val telecomManager: TelecomManager
) {

    private val calls = mutableListOf<Call>()

    var callService: BaseCallService? = null

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
        calls.add(call)
    }

    fun removeCall(call: Call) {
        calls.remove(call)
    }

    fun getCallForUser(contact: UserContact) = calls.firstOrNull {
        it.details?.handle?.schemeSpecificPart == contact.contactNumber
    }

}