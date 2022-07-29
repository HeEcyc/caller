package com.galaxy.call.ui.dial.fragment

import android.graphics.Color
import androidx.databinding.ObservableField
import com.galaxy.call.R
import com.galaxy.call.base.BaseViewModel
import com.galaxy.call.repository.PermissionRepository

class DialViewModel(
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    val text = ObservableField("")
    val adapter = DialAdapter(
        ::onButtonClick,
        R.drawable.dial_button_dial_fragment,
        Color.parseColor("#353131")
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