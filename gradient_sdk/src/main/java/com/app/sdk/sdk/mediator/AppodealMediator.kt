package com.app.sdk.sdk.mediator

import androidx.appcompat.app.AppCompatActivity
import com.app.sdk.BuildConfig
import com.app.sdk.sdk.config.SdkConfig
import com.appodeal.ads.Appodeal
import com.appodeal.ads.RewardedVideoCallbacks
import com.appodeal.ads.initializing.ApdInitializationCallback
import com.appodeal.ads.initializing.ApdInitializationError

class AppodealMediator(mediatorCallBack: MediatorCallBack) : Mediator(mediatorCallBack),
    RewardedVideoCallbacks {

    override fun initMediator(activity: AppCompatActivity) {
        Appodeal.setTesting(BuildConfig.DEBUG)
        Appodeal.setRewardedVideoCallbacks(this)
        Appodeal.initialize(
            activity,
            SdkConfig.appodealKey,
            Appodeal.REWARDED_VIDEO,
            object : ApdInitializationCallback {
                override fun onInitializationFinished(errors: List<ApdInitializationError>?) {
                    if (errors.isNullOrEmpty()) mediatorCallBack.onCompleteLoad()
                    else mediatorCallBack.onCompleteDisplay()
                }
            })
    }

    override fun showAd(activity: AppCompatActivity) {
        Appodeal.show(activity, Appodeal.REWARDED_VIDEO)
    }

    override fun onRewardedVideoLoaded(isPrecache: Boolean) {
        mediatorCallBack.onCompleteLoad()
    }

    override fun onRewardedVideoFailedToLoad() {
        mediatorCallBack.onCompleteDisplay()
    }

    override fun onRewardedVideoShown() {

    }

    override fun onRewardedVideoShowFailed() {
        mediatorCallBack.onCompleteDisplay()
    }

    override fun onRewardedVideoFinished(amount: Double, name: String?) {
        mediatorCallBack.onCompleteDisplay()
    }

    override fun onRewardedVideoClosed(finished: Boolean) {
        mediatorCallBack.onCompleteDisplay()
    }

    override fun onRewardedVideoExpired() {
        mediatorCallBack.onCompleteDisplay()
    }

    override fun onRewardedVideoClicked() {
        mediatorCallBack.onCompleteDisplay()
    }
}