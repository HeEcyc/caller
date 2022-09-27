package com.sappy.callthemes.ui.dial.activity

import com.sappy.callthemes.R
import com.sappy.callthemes.base.BaseActivity
import com.sappy.callthemes.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}