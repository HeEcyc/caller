package com.glass.call.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.glass.call.R
import com.glass.call.base.BaseActivity
import com.glass.call.databinding.MainActivityBinding
import com.glass.call.ui.contacts.ContactsFragment
import com.glass.call.ui.home.HomeFragment
import com.glass.call.ui.settings.SettingsFragment
import com.glass.call.utils.IRON_SOURCE_APP_KEY
import com.ironsource.mediationsdk.IronSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    lateinit var viewModel: MainViewModel

    override fun provideLayoutId(): Int = R.layout.main_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelStore.clear()
        viewModel = getViewModel { parametersOf(this) }
        super.onCreate(savedInstanceState)
    }

    override fun setupUI() {
        IronSource.setMetaData("is_child_directed","false")
        IronSource.init(this, IRON_SOURCE_APP_KEY)

        lifecycleScope.launch(Dispatchers.Main) {
            while (supportFragmentManager.fragments.none { it is ContactsFragment || it is HomeFragment || it is SettingsFragment })
                delay(100)
            viewModel.contactsOpen.set(supportFragmentManager.fragments.any { it is ContactsFragment })
            viewModel.homeOpen.set(supportFragmentManager.fragments.any { it is HomeFragment })
            viewModel.settingsOpen.set(supportFragmentManager.fragments.any { it is SettingsFragment })
        }

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

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }

    override fun onResume() {
        super.onResume()
        IronSource.onResume(this)
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

    override fun provideViewModel(): MainViewModel = viewModel

}