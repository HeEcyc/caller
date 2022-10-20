package com.glasserino.caller.ui.settings

import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlFragmentGl
import com.glasserino.caller.databinding.SettingsFragmentBinding
import com.glasserino.caller.ui.language.GlLanguageGlFragmentGl
import com.glasserino.caller.ui.main.GlMainGlActivityGl
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GlSettingsGlFragmentGl : GlBaseGlFragmentGl<GlSettingsGlViewGlModelGl, SettingsFragmentBinding>(R.layout.settings_fragment) {

    val viewModel: GlSettingsGlViewGlModelGl by viewModel { parametersOf(this) }

    override fun setupUI() {
        listOf<Any>().isEmpty()
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        listOf<Any>().isEmpty()
        binding.buttonPower.setOnClickListener {
            listOf<Any>().isEmpty()
            with(viewModel.permissionRepository) {
                listOf<Any>().isEmpty()
                if (hasCallerPermission)
                    openDefaultPhoneSelection(requireContext())
                else
                    askCallerPermission {}
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.buttonLanguage.setOnClickListener {
            listOf<Any>().isEmpty()
            activityAs<GlMainGlActivityGl>().addFragmentRemoveOther(GlLanguageGlFragmentGl())
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    override fun onResume() {
        listOf<Any>().isEmpty()
        super.onResume()
        listOf<Any>().isEmpty()
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        listOf<Any>().isEmpty()
    }


    override fun provideViewModel() = viewModel

}