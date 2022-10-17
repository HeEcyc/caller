package com.galaxy.call.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import com.galaxy.call.R
import com.galaxy.call.base.GBaseGFragmentG
import com.galaxy.call.databinding.SettingsFragmentBinding
import com.galaxy.call.ui.language.GLanguageGFragmentG
import com.galaxy.call.ui.main.GMainGActivityG
import com.galaxy.call.utils.appLink
import com.galaxy.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GSettingsGFragmentG : GBaseGFragmentG<GSettingsGViewGModelG, SettingsFragmentBinding>(R.layout.settings_fragment) {

    val viewModel: GSettingsGViewGModelG by viewModel { parametersOf(this) }

    override fun setupUI() {
        println("")
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        println("")
        binding.buttonPower.setOnClickListener {
            println("")
            with(viewModel.permissionRepository) {
                println("")
                if (hasCallerPermission)
                    openDefaultPhoneSelection(requireContext())
                else
                    askCallerPermission {}
                println("")
            }
            println("")
        }
        println("")
        binding.buttonLanguage.setOnClickListener {
            println("")
            activityAs<GMainGActivityG>().addFragmentRemoveOther(GLanguageGFragmentG())
            println("")
        }
        println("")
        binding.buttonShare.setOnClickListener(::shareApp)
        println("")
        binding.buttonRateUs.setOnClickListener(::openPlayMarketPage)
        println("")
        binding.buttonPrivacyPolicy.setOnClickListener(::openPlayMarketPage)
        println("")
    }

    fun openPlayMarketPage() {
        println("")
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(appLink)))
        println("")
    }

    fun shareApp() {
        println("")
        ShareCompat.IntentBuilder
            .from(requireActivity())
            .setType("text/plain")
            .setText("Install me\n$appLink")
            .createChooserIntent()
            .let(::startActivity)
        println("")
    }

    override fun onResume() {
        println("")
        super.onResume()
        println("")
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        println("")
    }


    override fun provideViewModel() = viewModel

}