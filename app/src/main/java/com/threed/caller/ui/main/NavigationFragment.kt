package com.threed.caller.ui.main

import androidx.fragment.app.activityViewModels
import com.threed.caller.R
import com.threed.caller.base.BaseFragment
import com.threed.caller.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}