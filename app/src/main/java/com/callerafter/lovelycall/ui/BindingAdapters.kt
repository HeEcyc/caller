package com.callerafter.lovelycall.ui

import android.graphics.Typeface
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("adapter")
fun setRVAdapter(rv: RecyclerView, rva: RecyclerView.Adapter<*>) {
    rv.adapter = rva
}

@BindingAdapter("textStyle")
fun setTextStyle(tv: TextView, style: Int) {
    tv.typeface = Typeface.create(tv.typeface, style)
}