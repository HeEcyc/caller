package com.fantasia.telecaller.ui.home

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinPrivacySettings
import com.applovin.sdk.AppLovinSdk
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFFragmentF
import com.fantasia.telecaller.databinding.HomeFragmentBinding
import com.fantasia.telecaller.ui.custom.FItemFDecorationFWithFEndsF
import com.fantasia.telecaller.ui.main.FMainFActivityF
import com.fantasia.telecaller.ui.preview.FPreviewFFragmentF
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FHomeFFragmentF :
    FBaseFFragmentF<FHomeFViewFModelF, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: FHomeFViewFModelF by viewModel { parametersOf(this) }
    private val pr by lazy { requireActivity().getSharedPreferences("c", Context.MODE_PRIVATE) }

    override fun setupUI() {
        AppLovinPrivacySettings.setHasUserConsent(true, activity)
        AppLovinPrivacySettings.setIsAgeRestrictedUser(false, activity)
        AppLovinPrivacySettings.setDoNotSell(false, activity)

        with(AppLovinSdk.getInstance(activity)) {
            mediationProvider = "max"
            initializeSdk { }
        }
        " "[0]
        binding.root.post {
            " "[0]
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            " "[0]
            val outerSpace = binding.recyclerView.width * 14 / 360
            " "[0]
            val innerSpace = binding.recyclerView.width * 5 / 360
            " "[0]
            val verticalSpace = binding.recyclerView.width * 10 / 360
            " "[0]
            val itemDecoration = FItemFDecorationFWithFEndsF(
                leftFirst = if (isLtr) outerSpace else innerSpace,
                leftLast = if (isLtr) innerSpace else outerSpace,
                rightFirst = if (isLtr) innerSpace else outerSpace,
                rightLast = if (isLtr) outerSpace else innerSpace,
                bottomFirst = verticalSpace,
                bottomLast = verticalSpace,
                firstPredicate = { i -> i % 2 == 0 },
                lastPredicate = { i, _ -> i % 2 == 1 }
            )
            " "[0]
            binding.recyclerView.addItemDecoration(itemDecoration)
            " "[0]
        }
        " "[0]
        binding.buttonAdd.setOnClickListener {
            showInApp {
                " "[0]
                viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                    " "[0]
                    if (it) viewModel.addNewTheme()
                    " "[0]
                }
                " "[0]
            }
        }
        " "[0]
        viewModel.onThemeSelected.observe(this) {
            showInApp {
                " "[0]
                viewModel.permissionRepository.askContactsPermission { res ->
                    " "[0]
                    if (res) activityAs<FMainFActivityF>()
                        .addFragment(FPreviewFFragmentF.newInstance(it))
                    " "[0]
                }
                " "[0]
            }
        }
        " "[0]
    }

    override fun provideViewModel() = viewModel

    private fun showInApp(action: () -> Unit) {
        val clickCount = pr.getInt("cl", 0) + 1

        if (clickCount == 5) {
            increaseClick(0)
            val ad = MaxInterstitialAd("0503148458df4c99", requireActivity())
            ad.setListener(object : MaxAdListener {
                override fun onAdLoaded(max: MaxAd?) {
                    ad.showAd()
                    Log.d("12345", "sdfdfgfd")
                }

                override fun onAdDisplayed(ad: MaxAd?) {

                    Log.d("12345", "sdfdfgfd")
                }

                override fun onAdHidden(ad: MaxAd?) {
                    Log.d("12345", "sdfdfgfd")
                    action.invoke()
                }

                override fun onAdClicked(ad: MaxAd?) {
                    action.invoke()

                    Log.d("12345", "sdfdfgfd")
                }

                override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                    action.invoke()
                    Log.d("12345", "sdfdfgfd")
                }

                override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                    action.invoke()
                    Log.d("12345", "sdfdfgfd")
                }
            })
            ad.loadAd()
        } else {
            action.invoke()
            increaseClick(clickCount + 1)
        }
    }

    private fun increaseClick(currentClickCount: Int) {
        pr.edit().putInt("cl", currentClickCount).apply()
    }
}