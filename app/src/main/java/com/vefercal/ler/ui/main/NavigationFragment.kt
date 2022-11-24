package com.vefercal.ler.ui.main

import androidx.fragment.app.activityViewModels
import com.vefercal.ler.R
import com.vefercal.ler.base.BaseFragment
import com.vefercal.ler.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}