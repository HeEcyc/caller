package com.glasserino.caller.ui.dial.activity

import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlActivityGl
import com.glasserino.caller.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class GlDialGlActivityGl : GlBaseGlActivityGl<GlDialGlActivityGLViewGLModelGL, DialActivityBinding>() {

    private val viewModel: GlDialGlActivityGLViewGLModelGL by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {listOf<Any>().isEmpty()}

    override fun provideViewModel() = viewModel

}