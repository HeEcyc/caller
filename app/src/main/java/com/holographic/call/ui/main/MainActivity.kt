package com.holographic.call.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.holographic.call.R
import com.holographic.call.base.BaseActivity
import com.holographic.call.databinding.MainActivityBinding
import com.holographic.call.utils.IRON_SOURCE_APP_KEY
import com.ironsource.mediationsdk.IronSource
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    val viewModel: MainViewModel by viewModel { parametersOf(this) }

    override fun provideLayoutId(): Int = R.layout.main_activity

    override fun setupUI() {
        IronSource.setMetaData("is_child_directed","false")
        IronSource.init(this, IRON_SOURCE_APP_KEY)
    }

    override fun provideViewModel(): MainViewModel = viewModel

    override fun onResume() {
        super.onResume()
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