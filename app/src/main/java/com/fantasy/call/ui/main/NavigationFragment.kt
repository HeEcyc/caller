package com.fantasy.call.ui.main

import androidx.fragment.app.activityViewModels
import com.fantasy.call.R
import com.fantasy.call.base.BaseFragment
import com.fantasy.call.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}