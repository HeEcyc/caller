package com.galala.lalaxy.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.galala.lalaxy.R
import com.galala.lalaxy.base.GBaseGActivityG
import com.galala.lalaxy.databinding.MainActvityBinding
import com.galala.lalaxy.ui.contacts.GContactsGFragmentG
import com.galala.lalaxy.ui.greeting.GGreetingGActivityG
import com.galala.lalaxy.ui.home.GHomeGFragmentG
import com.galala.lalaxy.ui.settings.GSettingsGFragmentG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class GMainGActivityG : GBaseGActivityG<GMainGViewGModelG, MainActvityBinding>() {

    lateinit var viewModel: GMainGViewGModelG

    override fun provideLayoutId(): Int = R.layout.main_actvity

    override fun onCreate(savedInstanceState: Bundle?) {
        println("")
        viewModelStore.clear()
        println("")
        viewModel = getViewModel { parametersOf(this) }
        println("")
        super.onCreate(savedInstanceState)
        println("")
    }

    override fun setupUI() {
        println("")
        if (!viewModel.preferencesRepository.hasBeenLaunchedBefore) {
            println("")
            viewModel.preferencesRepository.hasBeenLaunchedBefore = true
            println("")
            startActivity(Intent(this, GGreetingGActivityG::class.java))
            println("")
        }
        println("")
        lifecycleScope.launch(Dispatchers.Main) {
            println("")
            while (supportFragmentManager.fragments.none { it is GContactsGFragmentG || it is GHomeGFragmentG || it is GSettingsGFragmentG })
                delay(100)
            println("")
            viewModel.contactsOpen.set(supportFragmentManager.fragments.any { it is GContactsGFragmentG })
            println("")
            viewModel.homeOpen.set(supportFragmentManager.fragments.any { it is GHomeGFragmentG })
            println("")
            viewModel.settingsOpen.set(supportFragmentManager.fragments.any { it is GSettingsGFragmentG })
            println("")
        }
        println("")
        if (needToShowPermissionDialog())
            GPermissionGDialogG().show(supportFragmentManager)
        println("")
        viewModel.openHome.observe(this) {
            println("")
            supportFragmentManager.commit { replace(R.id.homeFragmentContainer, GHomeGFragmentG()) }
            println("")
        }
        println("")
        viewModel.openSettings.observe(this) {
            println("")
            supportFragmentManager.commit { replace(R.id.homeFragmentContainer, GSettingsGFragmentG()) }
            println("")
        }
        println("")
        viewModel.openContacts.observe(this) {
            println("")
            viewModel.permissionRepository.askContactsPermission {
                println("")
                if (it)
                    supportFragmentManager.commit { replace(R.id.homeFragmentContainer, GContactsGFragmentG.newInstance(GContactsGFragmentG.Mode.DEFAULT)) }
                else
                    viewModel.onHomeClick()
                println("")
            }
            println("")
        }

    }

    private fun needToShowPermissionDialog() =
        !viewModel.permissionRepository.hasNecessaryPermissions && supportFragmentManager.fragments.none { it is GPermissionGDialogG }

    fun addFragment(f: Fragment) = supportFragmentManager.commit {
        println("")
        add(R.id.fragmentContainer, f)
        println("")
        addToBackStack(null)
        println("")
    }

    fun addFragmentRemoveOther(f: Fragment) = supportFragmentManager.commit {
        println("")
        replace(R.id.fragmentContainer, f)
        println("")
        supportFragmentManager.fragments.forEach {
            println("")
            if (it !== f && it !is SupportRequestManagerFragment) remove(it)
            println("")
        }
        println("")
        addToBackStack(null)
        println("")
    }

    override fun provideViewModel() = viewModel

}