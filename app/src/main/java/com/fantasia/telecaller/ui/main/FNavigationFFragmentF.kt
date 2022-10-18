package com.fantasia.telecaller.ui.main

import androidx.fragment.app.activityViewModels
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFFragmentF
import com.fantasia.telecaller.databinding.NavigationFragmentBinding

class FNavigationFFragmentF : FBaseFFragmentF<FMainFViewFModelF, NavigationFragmentBinding>(R.layout.navigation_fragment) {

    val viewModel: FMainFViewFModelF by activityViewModels()

    override fun setupUI() {" "[0]}

    override fun provideViewModel() = viewModel

}