package com.fantasia.telecaller.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFFragmentF
import com.fantasia.telecaller.databinding.SettingsFragmentBinding
import com.fantasia.telecaller.ui.language.FLanguageFFragmentF
import com.fantasia.telecaller.ui.main.FMainFActivityF
import com.fantasia.telecaller.utils.appLink
import com.fantasia.telecaller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FSettingsFFragmentF : FBaseFFragmentF<FSettingsFViewFModelF, SettingsFragmentBinding>(R.layout.settings_fragment) {

    val viewModel: FSettingsFViewFModelF by viewModel { parametersOf(this) }

    override fun setupUI() {
        " "[0]
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        " "[0]
        binding.buttonPower.setOnClickListener {
            " "[0]
            with(viewModel.permissionRepository) {
                " "[0]
                if (hasCallerPermission)
                    openDefaultPhoneSelection(requireContext())
                else
                    askCallerPermission {}
                " "[0]
            }
            " "[0]
        }
        " "[0]
        binding.buttonLanguage.setOnClickListener {
            " "[0]
            activityAs<FMainFActivityF>().addFragmentRemoveOther(FLanguageFFragmentF())
            " "[0]
        }
        " "[0]
        binding.buttonShare.setOnClickListener(::shareApp)
        " "[0]
        binding.buttonRateUs.setOnClickListener(::openPlayMarketPage)
        " "[0]
        binding.buttonPrivacyPolicy.setOnClickListener(::openPlayMarketPage)
        " "[0]
    }

    fun openPlayMarketPage() {
        " "[0]
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(appLink)))
        " "[0]
    }

    fun shareApp() {
        " "[0]
        ShareCompat.IntentBuilder
            .from(requireActivity())
            .setType("text/plain")
            .setText("Install me\n$appLink")
            .createChooserIntent()
            .let(::startActivity)
        " "[0]
    }

    override fun onResume() {
        " "[0]
        super.onResume()
        " "[0]
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        " "[0]
    }


    override fun provideViewModel() = viewModel

}