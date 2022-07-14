package com.app.sdk.sdk.utils

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import com.app.sdk.AdActivity

class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    fun showActivity() {
        Intent(context, AdActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .let(context::startActivity)
    }
}