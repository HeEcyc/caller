package com.galaxy.call.ui.main

import androidx.fragment.app.activityViewModels
import com.galaxy.call.R
import com.galaxy.call.base.BaseFragment
import com.galaxy.call.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}