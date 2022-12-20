package com.pecall.poscreen.ui.dial.activity

import com.pecall.poscreen.R
import com.pecall.poscreen.base.BaseActivity
import com.pecall.poscreen.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}