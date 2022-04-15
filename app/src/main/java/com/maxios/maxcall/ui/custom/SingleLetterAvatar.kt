package com.maxios.maxcall.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.maxios.maxcall.databinding.ViewSingleLetterAvatarBinding

class SingleLetterAvatar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    var name: String? = null
        set(value) {
            field = value
            updateTextView()
        }

    private val binding = ViewSingleLetterAvatarBinding
        .inflate(LayoutInflater.from(context), this, true)

    companion object {
        @JvmStatic
        @BindingAdapter("text")
        fun setText(sla: SingleLetterAvatar, s: String?) {
            sla.name = s
        }
    }

    init {
        post { updateTextView() }
    }

    private fun updateTextView() {
        binding.textView.text = name?.trim()?.firstOrNull()?.uppercaseChar()?.toString() ?: "N"
    }

}