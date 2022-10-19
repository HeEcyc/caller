package com.glasserino.caller.utils

import android.telecom.Call
import android.telecom.VideoProfile
import android.view.View
import androidx.databinding.Observable

fun Observable.addOnPropertyChangedCallback(
    callback: (Observable, Int) -> Unit
): Observable.OnPropertyChangedCallback =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) =
            callback(sender, propertyId)
    }.also { addOnPropertyChangedCallback(it) }

fun View.setOnClickListener(lambda: () -> Unit) = setOnClickListener { lambda() }

fun Call.hangup() = disconnect()

fun Call.answer() = answer(VideoProfile.STATE_AUDIO_ONLY)