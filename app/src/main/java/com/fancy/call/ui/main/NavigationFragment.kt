package com.fancy.call.ui.main

import androidx.fragment.app.activityViewModels
import com.fancy.call.R
import com.fancy.call.base.BaseFragment
import com.fancy.call.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}