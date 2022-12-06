package com.app.sdk.sdk.mediator

import androidx.activity.ComponentActivity

abstract class Mediator(val mediatorCallBack: MediatorCallBack) {

    abstract fun initMediator(activity: ComponentActivity)

    abstract fun showAd(activity: ComponentActivity)

    interface MediatorCallBack {

        fun onCompleteLoad(mediator: Mediator)

        fun onError()

        fun onHide()

        fun onClicked()

        fun onDisplay()

    }
}