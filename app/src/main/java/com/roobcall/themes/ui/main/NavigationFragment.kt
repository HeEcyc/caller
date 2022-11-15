package com.roobcall.themes.ui.main

import androidx.fragment.app.activityViewModels
import com.roobcall.themes.R
import com.roobcall.themes.base.BaseFragment
import com.roobcall.themes.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}