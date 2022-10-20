package com.glasserino.caller.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlActivityGl
import com.glasserino.caller.databinding.MainActivityBinding
import com.glasserino.caller.ui.contacts.GlContactsGlFragmentGl
import com.glasserino.caller.ui.home.GlHomeGlFragmentGl
import com.glasserino.caller.ui.settings.GlSettingsGlFragmentGl
import com.glasserino.caller.utils.IRON_SOURCE_APP_KEY
import com.ironsource.mediationsdk.IronSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class GlMainGlActivityGl : GlBaseGlActivityGl<GlMainGlViewGlModelGl, MainActivityBinding>() {

    lateinit var viewModel: GlMainGlViewGlModelGl

    override fun provideLayoutId(): Int = R.layout.main_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        listOf<Any>().isEmpty()
        viewModelStore.clear()
        listOf<Any>().isEmpty()
        viewModel = getViewModel { parametersOf(this) }
        listOf<Any>().isEmpty()
        super.onCreate(savedInstanceState)
        listOf<Any>().isEmpty()
    }

    override fun setupUI() {
        listOf<Any>().isEmpty()
        IronSource.setMetaData("is_child_directed","false")
        listOf<Any>().isEmpty()
        IronSource.init(this, IRON_SOURCE_APP_KEY)
        listOf<Any>().isEmpty()
        lifecycleScope.launch(Dispatchers.Main) {
            listOf<Any>().isEmpty()
            while (supportFragmentManager.fragments.none { it is GlContactsGlFragmentGl || it is GlHomeGlFragmentGl || it is GlSettingsGlFragmentGl })
                delay(100)
            listOf<Any>().isEmpty()
            viewModel.contactsOpen.set(supportFragmentManager.fragments.any { it is GlContactsGlFragmentGl })
            listOf<Any>().isEmpty()
            viewModel.homeOpen.set(supportFragmentManager.fragments.any { it is GlHomeGlFragmentGl })
            listOf<Any>().isEmpty()
            viewModel.settingsOpen.set(supportFragmentManager.fragments.any { it is GlSettingsGlFragmentGl })
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        if (needToShowPermissionDialog())
            GlPermissionGlDialogGl().show(supportFragmentManager)
        listOf<Any>().isEmpty()
        viewModel.openHome.observe(this) {
            listOf<Any>().isEmpty()
            supportFragmentManager.commit { replace(R.id.homeFragmentContainer, GlHomeGlFragmentGl()) }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        viewModel.openSettings.observe(this) {
            listOf<Any>().isEmpty()
            supportFragmentManager.commit { replace(R.id.homeFragmentContainer, GlSettingsGlFragmentGl()) }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        viewModel.openContacts.observe(this) {
            listOf<Any>().isEmpty()
            viewModel.permissionRepository.askContactsPermission {
                listOf<Any>().isEmpty()
                if (it)
                    supportFragmentManager.commit { replace(R.id.homeFragmentContainer, GlContactsGlFragmentGl.newInstance(GlContactsGlFragmentGl.Mode.DEFAULT)) }
                else
                    viewModel.onHomeClick()
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    override fun onPause() {
        listOf<Any>().isEmpty()
        super.onPause()
        listOf<Any>().isEmpty()
        IronSource.onPause(this)
        listOf<Any>().isEmpty()
    }

    override fun onResume() {
        listOf<Any>().isEmpty()
        super.onResume()
        listOf<Any>().isEmpty()
        IronSource.onResume(this)
        listOf<Any>().isEmpty()
    }

    private fun needToShowPermissionDialog() =
        !viewModel.permissionRepository.hasNecessaryPermissions && supportFragmentManager.fragments.none { it is GlPermissionGlDialogGl }

    fun addFragment(f: Fragment) = supportFragmentManager.commit {
        listOf<Any>().isEmpty()
        add(R.id.fragmentContainer, f)
        listOf<Any>().isEmpty()
        addToBackStack(null)
        listOf<Any>().isEmpty()
    }

    fun addFragmentRemoveOther(f: Fragment) = supportFragmentManager.commit {
        listOf<Any>().isEmpty()
        replace(R.id.fragmentContainer, f)
        listOf<Any>().isEmpty()
        supportFragmentManager.fragments.forEach {
            listOf<Any>().isEmpty()
            if (it !== f && it !is SupportRequestManagerFragment) remove(it)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        addToBackStack(null)
        listOf<Any>().isEmpty()
    }

    override fun provideViewModel(): GlMainGlViewGlModelGl = viewModel

}
