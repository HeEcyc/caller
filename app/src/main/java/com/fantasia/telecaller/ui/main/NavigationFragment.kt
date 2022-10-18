package com.fantasia.telecaller.ui.main

import androidx.fragment.app.activityViewModels
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.BaseFragment
import com.fantasia.telecaller.databinding.NavigationFragmentBinding

class NavigationFragment : BaseFragment<MainViewModel, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}