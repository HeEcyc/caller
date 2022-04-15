package com.maxios.maxcall.ui.main

import androidx.fragment.app.activityViewModels
import com.maxios.maxcall.R
import com.maxios.maxcall.base.BaseFragment
import com.maxios.maxcall.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {

    }

    override fun provideViewModel() = viewModel

}