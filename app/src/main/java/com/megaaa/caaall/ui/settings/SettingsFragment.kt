package com.megaaa.caaall.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import androidx.fragment.app.activityViewModels
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseFragment
import com.megaaa.caaall.databinding.SettingsFragmentBinding
import com.megaaa.caaall.ui.language.LanguageFragment
import com.megaaa.caaall.ui.main.MainActivity
import com.megaaa.caaall.ui.main.MainViewModel
import com.megaaa.caaall.utils.appLink
import com.megaaa.caaall.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsFragmentBinding>(R.layout.settings_fragment) {

    private val viewModel: SettingsViewModel by viewModel()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        binding.switchPower.isChecked = mainViewModel.permissionRepository.hasCallerPermission
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        binding.buttonPower.setOnClickListener {
            with(mainViewModel.permissionRepository) {
                if (hasCallerPermission)
                    openDefaultPhoneSelection(requireContext())
                else
                    askCallerPermission {}
            }
        }
        binding.buttonLanguages.setOnClickListener {
            activityAs<MainActivity>().replaceFragmentsAddToBackStack(LanguageFragment())
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
        binding.switchPower.isChecked = mainViewModel.permissionRepository.hasCallerPermission
    }

    override fun provideViewModel() = viewModel

}