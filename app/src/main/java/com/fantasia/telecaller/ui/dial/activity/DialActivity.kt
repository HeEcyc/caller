package com.fantasia.telecaller.ui.dial.activity

import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.BaseActivity
import com.fantasia.telecaller.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}