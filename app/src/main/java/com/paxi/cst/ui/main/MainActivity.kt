package com.paxi.cst.ui.main

import android.content.Intent
import android.os.Bundle
import com.paxi.cst.R
import com.paxi.cst.base.BaseActivity
import com.paxi.cst.databinding.MainActvityBinding
import com.paxi.cst.ui.greeting.GreetingActivity
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
        if (!viewModel.preferencesRepository.hasBeenLaunchedBefore) {
            viewModel.preferencesRepository.hasBeenLaunchedBefore = true
            startActivity(Intent(this, GreetingActivity::class.java))
        }

        if (needToShowPermissionDialog())
            PermissionDialog().show(supportFragmentManager)

    }

    private fun needToShowPermissionDialog() =
        !viewModel.permissionRepository.hasNecessaryPermissions && supportFragmentManager.fragments.none { it is PermissionDialog }

    override fun provideViewModel() = viewModel

}