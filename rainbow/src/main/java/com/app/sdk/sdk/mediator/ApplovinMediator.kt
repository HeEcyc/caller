package com.app.sdk.sdk.mediator

import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.sdk.config.SdkConfig
import com.app.sdk.sdk.utils.writeLog
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd
import com.applovin.sdk.AppLovinPrivacySettings
import com.applovin.sdk.AppLovinSdk

class ApplovinMediator constructor(mediatorCallBack: MediatorCallBack) :
    Mediator(mediatorCallBack), MaxRewardedAdListener {
    private var reward: MaxRewardedAd? = null

    override fun initMediator(activity: AppCompatActivity) {
        AppLovinPrivacySettings.setHasUserConsent(true, activity)
        AppLovinPrivacySettings.setIsAgeRestrictedUser(false, activity)
        AppLovinPrivacySettings.setDoNotSell(false, activity)

        with(AppLovinSdk.getInstance(activity)) {
            if (isInitialized) mediatorCallBack.onCompleteLoad()
            else {
                mediationProvider = "max"
                initializeSdk { mediatorCallBack.onCompleteLoad() }
            }
        }
    }

    override fun showAd(activity: AppCompatActivity) {
        reward = MaxRewardedAd.getInstance(SdkConfig.adUnitId, activity)
            .apply {
                setListener(this@ApplovinMediator)
                this.loadAd()
            }
    }

    override fun onAdLoaded(ad: MaxAd) {
        reward?.showAd()
    }

    override fun onAdDisplayed(ad: MaxAd) {

    }

    override fun onAdHidden(ad: MaxAd?) {

    }

    override fun onAdClicked(ad: MaxAd?) {
        mediatorCallBack.onCompleteDisplay()
    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        mediatorCallBack.onCompleteDisplay()
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        mediatorCallBack.onCompleteDisplay()
    }

    override fun onRewardedVideoStarted(ad: MaxAd?) {

    }

    override fun onRewardedVideoCompleted(ad: MaxAd?) {
        mediatorCallBack.onCompleteDisplay()
    }

    override fun onUserRewarded(ad: MaxAd?, reward: MaxReward?) {

    }
}