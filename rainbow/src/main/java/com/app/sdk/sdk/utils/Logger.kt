package com.app.sdk.sdk.utils

import android.util.Log
import com.app.sdk.BuildConfig

fun Any.writeLog(message: String) {
    if (BuildConfig.DEBUG) Log.d("12345", "${this::class.java.simpleName} $message")
}