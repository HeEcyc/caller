package com.fusiecal.ler.ui.dial.activity

import com.fusiecal.ler.R
import com.fusiecal.ler.base.BaseActivity
import com.fusiecal.ler.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}