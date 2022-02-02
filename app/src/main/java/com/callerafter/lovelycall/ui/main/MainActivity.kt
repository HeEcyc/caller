package com.callerafter.lovelycall.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseActivity
import com.callerafter.lovelycall.databinding.MainActivityBinding
import com.callerafter.lovelycall.repository.PermissionRepository
import com.callerafter.lovelycall.ui.main.permission.PermissionDialog
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

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
            .filterNot { it is SupportRequestManagerFragment }
        if (fragments.size > 1)
            supportFragmentManager.commit { remove(fragments.last()) }
        else
            super.onBackPressed()
    }

    fun <T : Fragment> fragment(fragmentClass: Class<T>): T? =
        supportFragmentManager.fragments.firstOrNull {
            fragmentClass.isInstance(it)
        } as? T

}