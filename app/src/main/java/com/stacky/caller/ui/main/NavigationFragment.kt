package com.stacky.caller.ui.main

import androidx.fragment.app.activityViewModels
import com.stacky.caller.R
import com.stacky.caller.base.BaseFragment
import com.stacky.caller.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}