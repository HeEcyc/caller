package com.maxios.maxcall.ui.main

import com.maxios.maxcall.R
import com.maxios.maxcall.base.BaseFragment
import com.maxios.maxcall.databinding.NavigationFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by viewModel()

    override fun setupUI() {

    }

    override fun provideViewModel() = viewModel

}