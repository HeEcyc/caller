package com.yellowmood.caller.ui.dial.activity

import com.yellowmood.caller.R
import com.yellowmood.caller.base.BaseActivity
import com.yellowmood.caller.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}