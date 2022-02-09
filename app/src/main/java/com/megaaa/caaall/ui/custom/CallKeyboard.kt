package com.megaaa.caaall.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.megaaa.caaall.R
import com.megaaa.caaall.databinding.ViewCallKeyboardBinding

class CallKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val binding: ViewCallKeyboardBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_call_keyboard,
        this,
        true
    )

}