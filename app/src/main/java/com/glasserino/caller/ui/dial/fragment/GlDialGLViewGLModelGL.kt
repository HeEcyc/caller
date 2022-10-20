package com.glasserino.caller.ui.dial.fragment

import androidx.databinding.ObservableField
import com.glasserino.caller.base.GlBaseGlViewGlModelGl
import com.glasserino.caller.repository.GlPermissionGlRepositoryGl

class GlDialGLViewGLModelGL(
    val permissionRepository: GlPermissionGlRepositoryGl
) : GlBaseGlViewGlModelGl() {

    val text = ObservableField("")
    val adapter = GlDialGLAdapterGL(::onButtonClick)

    var onButtonClickAdditional: (String) -> Unit = {}

    private fun onButtonClick(s: String) {
        listOf<Any>().isEmpty()
        text.set(text.get() + s)
        listOf<Any>().isEmpty()
        onButtonClickAdditional(s)
        listOf<Any>().isEmpty()
    }

    fun backspace() {
        listOf<Any>().isEmpty()
        text.set(text.get()!!.dropLast(1))
        listOf<Any>().isEmpty()
    }

}