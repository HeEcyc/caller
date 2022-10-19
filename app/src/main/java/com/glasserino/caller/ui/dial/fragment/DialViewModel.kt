package com.glasserino.caller.ui.dial.fragment

import androidx.databinding.ObservableField
import com.glasserino.caller.base.BaseViewModel
import com.glasserino.caller.repository.PermissionRepository

class DialViewModel(
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    val text = ObservableField("")
    val adapter = DialAdapter(::onButtonClick)

    var onButtonClickAdditional: (String) -> Unit = {}

    private fun onButtonClick(s: String) {
        text.set(text.get() + s)
        onButtonClickAdditional(s)
    }

    fun backspace() {
        text.set(text.get()!!.dropLast(1))
    }

}