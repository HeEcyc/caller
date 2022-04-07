package com.holographic.call.ui.dial.activity

import com.holographic.call.R
import com.holographic.call.base.BaseActivity
import com.holographic.call.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}