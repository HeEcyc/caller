package com.maxios.maxcall.ui.main

import com.ironsource.mediationsdk.IronSource
import com.maxios.maxcall.R
import com.maxios.maxcall.base.BaseActivity
import com.maxios.maxcall.databinding.MainActivityBinding
import com.maxios.maxcall.utils.IRON_SOURCE_API_KEY
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    val viewModel: MainViewModel by viewModel()

    override fun provideLayoutId(): Int = R.layout.main_activity

    override fun setupUI() {
        IronSource.setMetaData("is_child_directed","false")
        IronSource.init(this, IRON_SOURCE_API_KEY)
    }

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }

    override fun onResume() {
        super.onResume()
        IronSource.onResume(this)
    }

    override fun provideViewModel(): MainViewModel = viewModel

}