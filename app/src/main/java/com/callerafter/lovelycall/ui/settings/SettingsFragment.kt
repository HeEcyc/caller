package com.callerafter.lovelycall.ui.settings

import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseFragment
import com.callerafter.lovelycall.databinding.SettingsFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFragmentBinding>(R.layout.settings_fragment) {

    private val viewModel: SettingsViewModel by viewModel()

    override fun setupUI() {

    }

    override fun provideViewModel() = viewModel

}