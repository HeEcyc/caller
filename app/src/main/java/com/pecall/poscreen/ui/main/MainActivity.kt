package com.pecall.poscreen.ui.main

import android.content.Intent
import android.os.Bundle
import com.app.sdk.sdk.PremiumUserSdk
import com.pecall.poscreen.R
import com.pecall.poscreen.base.BaseActivity
import com.pecall.poscreen.databinding.MainActvityBinding
import com.pecall.poscreen.ui.greeting.GreetingActivity
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<MainViewModel, MainActvityBinding>() {

    lateinit var viewModel: MainViewModel

    override fun provideLayoutId(): Int = R.layout.main_actvity

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelStore.clear()
        viewModel = getViewModel { parametersOf(this) }
        super.onCreate(savedInstanceState)
    }

    override fun setupUI() {
        if (needToShowPermissionDialog()) PermissionDialog().show(supportFragmentManager)
    }

    private fun needToShowPermissionDialog() =
        !viewModel.permissionRepository.hasNecessaryPermissions && supportFragmentManager.fragments.none { it is PermissionDialog }

    override fun provideViewModel() = viewModel

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        PremiumUserSdk.onResult(this)
    }

}