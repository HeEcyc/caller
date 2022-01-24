package com.callerafter.lovelycall.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import androidx.fragment.app.activityViewModels
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseFragment
import com.callerafter.lovelycall.databinding.SettingsFragmentBinding
import com.callerafter.lovelycall.ui.main.MainActivity
import com.callerafter.lovelycall.ui.main.MainViewModel
import com.callerafter.lovelycall.utils.appLink
import com.callerafter.lovelycall.utils.setOnClickListener
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
                    openDefaultPhoneSelection()
                else
                    askCallerPermission {}
            }
        }
        binding.buttonLanguages.setOnClickListener {
//            activityAs<MainActivity>().replaceFragmentsAddToBackStack()
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