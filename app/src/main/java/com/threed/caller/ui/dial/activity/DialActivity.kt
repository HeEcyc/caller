package com.threed.caller.ui.dial.activity

import com.threed.caller.R
import com.threed.caller.base.BaseActivity
import com.threed.caller.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}