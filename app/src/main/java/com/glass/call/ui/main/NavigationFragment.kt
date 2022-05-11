package com.glass.call.ui.main

import androidx.fragment.app.activityViewModels
import com.glass.call.R
import com.glass.call.base.BaseFragment
import com.glass.call.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}