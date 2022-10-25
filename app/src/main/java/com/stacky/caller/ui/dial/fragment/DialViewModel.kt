package com.stacky.caller.ui.dial.fragment

import android.graphics.Color
import androidx.databinding.ObservableField
import com.stacky.caller.R
import com.stacky.caller.base.BaseViewModel
import com.stacky.caller.repository.PermissionRepository

class DialViewModel(
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    val text = ObservableField("")
    val adapter = DialAdapter(
        ::onButtonClick,
        R.drawable.dial_button,
        Color.parseColor("#FFFFFF")
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