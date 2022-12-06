package com.app.sdk

import android.app.ActivityManager.TaskDescription
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap
import com.app.sdk.sdk.SoundSdk
import com.app.sdk.sdk.mediator.ApplovinMediator
import com.app.sdk.sdk.mediator.Mediator


class DymmyActivity : AppCompatActivity(), Mediator.MediatorCallBack {
    private var currentMediator: Mediator? = null

    private val listOfApps = listOf(
        "com.facebook.orca",
        "com.facebook.katana",
        "com.example.facebook",
        "com.facebook.android",
        "com.instagram.android",
        "com.ss.android.ugc.trill"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empty)
        show()
    }

    private fun show() {
        setTaskDescription()
        currentMediator = ApplovinMediator(this)
        currentMediator?.initMediator(this)
    }

    private fun setTaskDescription() {
        getApplicationInfoForApp()
            .let(::getTaskDescription)
            .let(::setTaskDescription)
    }


    @Suppress("DEPRECATION")
    private fun getTaskDescription(info: ApplicationInfo?) = if (info != null)
        TaskDescription(info.loadLabel(packageManager).toString(), getLogoBitmap(info), Color.WHITE)
    else TaskDescription(" ", getLogoBitmap(info), Color.BLACK)

    private fun getLogoBitmap(info: ApplicationInfo?) = createBitmap(100, 100).apply {
        val canvas = Canvas(this)
        info?.loadIcon(packageManager)?.let {
            it.setBounds(0, 0, width, height)
            it.draw(canvas)
        } ?: eraseColor(Color.BLACK)
    }

    private fun getApplicationInfoForApp() = listOfApps
        .mapNotNull(::existApplicationInfo)
        .randomOrNull()

    private fun existApplicationInfo(packageName: String) = try {
        packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    } catch (e: Exception) {
        null
    }

    override fun onCompleteLoad(mediator: Mediator) {
        mediator.showAd(this)
    }

    override fun onError() {
        onHide()
    }

    override fun onHide() {
        SoundSdk.saveShowingTime(this)
        finishAndRemoveTask()
    }

    override fun onClicked() {
        onHide()
    }

    override fun onDisplay() {

    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN) onHide()
    }

}
