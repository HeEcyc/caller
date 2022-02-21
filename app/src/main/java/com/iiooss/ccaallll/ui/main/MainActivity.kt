package com.iiooss.ccaallll.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.iiooss.ccaallll.R
import com.iiooss.ccaallll.base.BaseActivity
import com.iiooss.ccaallll.databinding.MainActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    val viewModel: MainViewModel by viewModel { parametersOf(this) }

    override fun provideLayoutId(): Int = R.layout.main_activity

    override fun setupUI() {

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