package com.galala.lalaxy.ui.main

import androidx.fragment.app.activityViewModels
import com.galala.lalaxy.R
import com.galala.lalaxy.base.GBaseGFragmentG
import com.galala.lalaxy.databinding.NavigationFragmentBinding

class GNavigationGFragmentG : GBaseGFragmentG<GMainGViewGModelG, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: GMainGViewGModelG by activityViewModels()

    override fun setupUI() {
        println("")
    }

    override fun provideViewModel() = viewModel

}