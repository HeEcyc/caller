package com.paxi.cst.ui.settings

import android.view.View
import com.paxi.cst.R
import com.paxi.cst.base.BaseActivity
import com.paxi.cst.databinding.SettingsActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SettingsActivity : BaseActivity<SettingsViewModel, SettingsActivityBinding>() {

    val viewModel: SettingsViewModel by viewModel { parametersOf(this) }

    override fun provideLayoutId() = R.layout.settings_activity

    override fun setupUI() {
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        binding.buttonPower.setOnClickListener {
            with(viewModel.permissionRepository) {
                if (hasCallerPermission)
                    openDefaultPhoneSelection(this@SettingsActivity)
                else
                    askCallerPermission {}
            }
        }
        binding.buttonBackSettings.setOnClickListener { finish() }
        binding.buttonLanguage.setOnClickListener {
            binding.menuSettings.visibility = View.GONE
            binding.headerSettings.visibility = View.GONE
            binding.menuLanguage.visibility = View.VISIBLE
            binding.headerLanguage.visibility = View.VISIBLE
        }
        binding.buttonBackLanguage.setOnClickListener {
            binding.menuLanguage.visibility = View.GONE
            binding.headerLanguage.visibility = View.GONE
            binding.menuSettings.visibility = View.VISIBLE
            binding.headerSettings.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
    }

    override fun provideViewModel() = viewModel

}