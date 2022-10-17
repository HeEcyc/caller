package com.galaxy.call.ui.dial.activity

import com.galaxy.call.R
import com.galaxy.call.base.GBaseGActivityG
import com.galaxy.call.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class GDialGActivityG : GBaseGActivityG<GDialGActivityGViewGModelG, DialActivityBinding>() {

    private val viewModel: GDialGActivityGViewGModelG by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {println("")}

    override fun provideViewModel() = viewModel

}