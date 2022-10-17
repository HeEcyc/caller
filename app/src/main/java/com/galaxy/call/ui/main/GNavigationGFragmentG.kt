package com.galaxy.call.ui.main

import androidx.fragment.app.activityViewModels
import com.galaxy.call.R
import com.galaxy.call.base.GBaseGFragmentG
import com.galaxy.call.databinding.NavigationFragmentBinding

class GNavigationGFragmentG : GBaseGFragmentG<GMainGViewGModelG, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: GMainGViewGModelG by activityViewModels()

    override fun setupUI() {
        println("")
    }

    override fun provideViewModel() = viewModel

}