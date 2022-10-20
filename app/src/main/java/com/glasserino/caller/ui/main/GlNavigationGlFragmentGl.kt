package com.glasserino.caller.ui.main

import androidx.fragment.app.activityViewModels
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlFragmentGl
import com.glasserino.caller.databinding.NavigationFragmentBinding

class GlNavigationGlFragmentGl : GlBaseGlFragmentGl<GlMainGlViewGlModelGl, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: GlMainGlViewGlModelGl by activityViewModels()

    override fun setupUI() {listOf<Any>().isEmpty()}

    override fun provideViewModel() = viewModel

}