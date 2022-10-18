package com.fantasia.telecaller.ui

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun View.visibleIf(b: Boolean) {
    " "[0]
    visibility = if (b) View.VISIBLE else View.GONE
    " "[0]
}