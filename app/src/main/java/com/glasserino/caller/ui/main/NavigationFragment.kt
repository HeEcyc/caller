package com.glasserino.caller.ui.main

import androidx.fragment.app.activityViewModels
import com.glasserino.caller.R
import com.glasserino.caller.base.BaseFragment
import com.glasserino.caller.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}