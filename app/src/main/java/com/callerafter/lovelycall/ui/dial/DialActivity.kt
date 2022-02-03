package com.callerafter.lovelycall.ui.dial

import android.view.View
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseActivity
import com.callerafter.lovelycall.databinding.DialActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialViewModel, DialActivityBinding>() {

    val viewModel: DialViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {
        binding.keyboard.binding.buttonCall.visibility = View.VISIBLE
        binding.keyboard.binding.textView.text = intent?.data?.schemeSpecificPart
        binding.keyboard.binding.buttonCall.setOnClickListener {
            TODO()
        }
    }

    override fun provideViewModel() = viewModel

}