package com.sappy.callthemes.ui.dial.fragment

import android.graphics.Color
import androidx.databinding.ObservableField
import com.sappy.callthemes.R
import com.sappy.callthemes.base.BaseViewModel
import com.sappy.callthemes.repository.PermissionRepository

class DialViewModel(
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    val text = ObservableField("")
    val adapter = DialAdapter(
        ::onButtonClick,
        R.drawable.dial_button_dial_fragment,
        Color.parseColor("#2D211E")
    )

    var onButtonClickAdditional: (String) -> Unit = {}

    private fun onButtonClick(s: String) {
        text.set(text.get() + s)
        onButtonClickAdditional(s)
    }

    fun backspace() {
        text.set(text.get()!!.dropLast(1))
    }

}