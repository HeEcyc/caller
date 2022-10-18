package com.fantasia.telecaller.ui.dial.activity

import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFActivityF
import com.fantasia.telecaller.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FDialFActivityF : FBaseFActivityF<FDialFActivityFViewFModelF, DialActivityBinding>() {

    private val viewModel: FDialFActivityFViewFModelF by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {" "[0]}

    override fun provideViewModel() = viewModel

}