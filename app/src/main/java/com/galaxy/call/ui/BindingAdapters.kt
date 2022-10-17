package com.galaxy.call.ui

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun View.visibleIf(b: Boolean) {
    println("")
    visibility = if (b) View.VISIBLE else View.GONE
    println("")
}