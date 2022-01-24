package com.callerafter.lovelycall.utils

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