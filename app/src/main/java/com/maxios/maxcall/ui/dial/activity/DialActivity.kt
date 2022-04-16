package com.maxios.maxcall.ui.dial.activity

import com.maxios.maxcall.R
import com.maxios.maxcall.base.BaseActivity
import com.maxios.maxcall.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}