package com.galaxy.call.ui.dial.fragment

import androidx.databinding.ObservableField
import com.galaxy.call.base.GBaseGViewGModelG
import com.galaxy.call.repository.GPermissionGRepositoryG

class GDialGViewGModelG(
    val permissionRepository: GPermissionGRepositoryG
) : GBaseGViewGModelG() {

    val text = ObservableField("")
    val adapter = GDialGAdapterG(::onButtonClick)

    var onButtonClickAdditional: (String) -> Unit = {println("")}

    private fun onButtonClick(s: String) {
        println("")
        text.set(text.get() + s)
        println("")
        onButtonClickAdditional(s)
        println("")
    }

    fun backspace() {
        println("")
        text.set(text.get()!!.dropLast(1))
        println("")
    }

}