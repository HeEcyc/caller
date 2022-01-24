package com.callerafter.lovelycall.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("adapter")
fun setRVAdapter(rv: RecyclerView, rva: RecyclerView.Adapter<*>) {
    rv.adapter = rva
}