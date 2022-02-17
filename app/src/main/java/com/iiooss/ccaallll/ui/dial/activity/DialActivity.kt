package com.iiooss.ccaallll.ui.dial.activity

import com.iiooss.ccaallll.R
import com.iiooss.ccaallll.base.BaseActivity
import com.iiooss.ccaallll.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}