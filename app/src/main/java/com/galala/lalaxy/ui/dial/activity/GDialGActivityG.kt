package com.galala.lalaxy.ui.dial.activity

import com.galala.lalaxy.R
import com.galala.lalaxy.base.GBaseGActivityG
import com.galala.lalaxy.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class GDialGActivityG : GBaseGActivityG<GDialGActivityGViewGModelG, DialActivityBinding>() {

    private val viewModel: GDialGActivityGViewGModelG by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {println("")}

    override fun provideViewModel() = viewModel

}