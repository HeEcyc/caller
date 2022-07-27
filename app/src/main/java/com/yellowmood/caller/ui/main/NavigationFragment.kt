package com.yellowmood.caller.ui.main

import androidx.fragment.app.activityViewModels
import com.yellowmood.caller.R
import com.yellowmood.caller.base.BaseFragment
import com.yellowmood.caller.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}