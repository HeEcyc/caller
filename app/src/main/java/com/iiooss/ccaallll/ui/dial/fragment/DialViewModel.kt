package com.iiooss.ccaallll.ui.dial.fragment

import androidx.databinding.ObservableField
import com.iiooss.ccaallll.base.BaseViewModel
import com.iiooss.ccaallll.repository.PermissionRepository

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