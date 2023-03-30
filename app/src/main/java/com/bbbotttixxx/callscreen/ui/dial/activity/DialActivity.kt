package com.bbbotttixxx.callscreen.ui.dial.activity

import com.bbbotttixxx.callscreen.R
import com.bbbotttixxx.callscreen.base.BaseActivity
import com.bbbotttixxx.callscreen.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialActivityViewModel, DialActivityBinding>() {

    private val viewModel: DialActivityViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {}

    override fun provideViewModel() = viewModel

}