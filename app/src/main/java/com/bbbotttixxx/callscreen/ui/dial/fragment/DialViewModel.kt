package com.bbbotttixxx.callscreen.ui.dial.fragment

import android.graphics.Color
import androidx.databinding.ObservableField
import com.bbbotttixxx.callscreen.R
import com.bbbotttixxx.callscreen.base.BaseViewModel
import com.bbbotttixxx.callscreen.repository.PermissionRepository

class DialViewModel(
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    val text = ObservableField("")
    val adapter = DialAdapter(
        ::onButtonClick
    )

    private fun onButtonClick(s: String) {
        text.set(text.get() + s)
    }

    fun backspace() {
        text.set(text.get()!!.dropLast(1))
    }

}