package com.megaaa.caaall.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseActivity
import com.megaaa.caaall.databinding.MainActivityBinding
import com.megaaa.caaall.repository.PermissionRepository
import com.megaaa.caaall.ui.main.permission.PermissionDialog
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {

    private val viewModel: MainViewModel by viewModel()

    override fun provideLayoutId(): Int = R.layout.main_activity

    override fun setupUI() {
        viewModel.permissionRepository = PermissionRepository(this)
    }

    override fun onResume() {
        super.onResume()
        if (needToShowPermissionDialog())
            PermissionDialog().show(supportFragmentManager)
    }

    private fun needToShowPermissionDialog() =
        !viewModel.permissionRepository.hasNecessaryPermissions && supportFragmentManager.fragments.none { it is PermissionDialog }

    override fun provideViewModel(): MainViewModel = viewModel

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