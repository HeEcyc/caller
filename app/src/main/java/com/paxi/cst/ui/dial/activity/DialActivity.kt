package com.paxi.cst.ui.dial.activity

import com.paxi.cst.R
import com.paxi.cst.base.BaseActivity
import com.paxi.cst.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}