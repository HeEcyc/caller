package com.roobcall.themes.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.roobcall.themes.R
import com.roobcall.themes.base.BaseActivity
import com.roobcall.themes.databinding.MainActvityBinding
import com.roobcall.themes.ui.contacts.ContactsFragment
import com.roobcall.themes.ui.greeting.GreetingActivity
import com.roobcall.themes.ui.home.HomeFragment
import com.roobcall.themes.ui.settings.SettingsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<MainViewModel, MainActvityBinding>() {

    lateinit var viewModel: MainViewModel

    override fun provideLayoutId(): Int = R.layout.main_actvity

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelStore.clear()
        viewModel = getViewModel { parametersOf(this) }
        super.onCreate(savedInstanceState)
    }

    override fun setupUI() {
        if (!viewModel.preferencesRepository.hasBeenLaunchedBefore) {
            viewModel.preferencesRepository.hasBeenLaunchedBefore = true
            startActivity(Intent(this, GreetingActivity::class.java))
        }

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

    override fun provideViewModel() = viewModel

}