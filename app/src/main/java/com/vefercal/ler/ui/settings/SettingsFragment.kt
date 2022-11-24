package com.vefercal.ler.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import com.vefercal.ler.R
import com.vefercal.ler.base.BaseFragment
import com.vefercal.ler.databinding.SettingsFragmentBinding
import com.vefercal.ler.ui.language.LanguageFragment
import com.vefercal.ler.ui.main.MainActivity
import com.vefercal.ler.utils.appLink
import com.vefercal.ler.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFragmentBinding>(R.layout.settings_fragment) {

    val viewModel: SettingsViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        binding.buttonPower.setOnClickListener {
            with(viewModel.permissionRepository) {
                if (hasCallerPermission)
                    openDefaultPhoneSelection(requireContext())
                else
                    askCallerPermission {}
            }
        }
        binding.buttonLanguage.setOnClickListener {
            activityAs<MainActivity>().addFragmentRemoveOther(LanguageFragment())
        }
        binding.buttonShare.setOnClickListener(::shareApp)
        binding.buttonRateUs.setOnClickListener(::openPlayMarketPage)
        binding.buttonPrivacyPolicy.setOnClickListener(::openPlayMarketPage)
    }

    fun openPlayMarketPage() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(appLink)))
    }

    fun shareApp() {
        ShareCompat.IntentBuilder
            .from(requireActivity())
            .setType("text/plain")
            .setText("Install me\n$appLink")
            .createChooserIntent()
            .let(::startActivity)
    }

    override fun onResume() {
        super.onResume()
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
    }


    override fun provideViewModel() = viewModel

}