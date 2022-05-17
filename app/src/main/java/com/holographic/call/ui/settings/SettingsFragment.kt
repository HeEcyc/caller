package com.holographic.call.ui.settings

import com.holographic.call.R
import com.holographic.call.base.BaseFragment
import com.holographic.call.databinding.SettingsFragmentBinding
import com.holographic.call.ui.language.LanguageFragment
import com.holographic.call.ui.main.MainActivity
import com.holographic.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFragmentBinding>(R.layout.settings_fragment) {

    val viewModel: SettingsViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        binding.buttonPower.setOnClickListener {
            with(viewModel.permissionRepository) {
                if (hasCallerPermission)
                    openDefaultPhoneSelection(requireContext())
                else
                    askCallerPermission {}
            }
        }
        binding.buttonLanguage.setOnClickListener {
            activityAs<MainActivity>().replaceFragmentsAddToBackStack(LanguageFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        binding.switchPower.toggle(viewModel.permissionRepository.hasCallerPermission)
    }


    override fun provideViewModel() = viewModel

}