package com.sappy.callthemes.ui.main

import androidx.fragment.app.activityViewModels
import com.sappy.callthemes.R
import com.sappy.callthemes.base.BaseFragment
import com.sappy.callthemes.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}