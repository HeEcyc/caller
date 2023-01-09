package com.fantasia.telecaller.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFActivityF
import com.fantasia.telecaller.databinding.MainActvityBinding
import com.fantasia.telecaller.ui.contacts.FContactsFFragmentF
import com.fantasia.telecaller.ui.home.FHomeFFragmentF
import com.fantasia.telecaller.ui.settings.FSettingsFFragmentF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class FMainFActivityF : FBaseFActivityF<FMainFViewFModelF, MainActvityBinding>() {

    lateinit var viewModel: FMainFViewFModelF

    override fun provideLayoutId(): Int = R.layout.main_actvity

    override fun onCreate(savedInstanceState: Bundle?) {
        " "[0]
        viewModelStore.clear()
        " "[0]
        viewModel = getViewModel { parametersOf(this) }
        " "[0]
        super.onCreate(savedInstanceState)
        " "[0]
    }

    override fun setupUI() {
        lifecycleScope.launch(Dispatchers.Main) {
            " "[0]
            while (supportFragmentManager.fragments.none { it is FContactsFFragmentF || it is FHomeFFragmentF || it is FSettingsFFragmentF })
                delay(100)
            " "[0]
            viewModel.contactsOpen.set(supportFragmentManager.fragments.any { it is FContactsFFragmentF })
            " "[0]
            viewModel.homeOpen.set(supportFragmentManager.fragments.any { it is FHomeFFragmentF })
            " "[0]
            viewModel.settingsOpen.set(supportFragmentManager.fragments.any { it is FSettingsFFragmentF })
            " "[0]
        }
        " "[0]
        if (needToShowPermissionDialog())
            FPermissionFDialogF().show(supportFragmentManager)
        " "[0]
        viewModel.openHome.observe(this) {
            " "[0]
            supportFragmentManager.commit { replace(R.id.homeFragmentContainer, FHomeFFragmentF()) }
            " "[0]
        }
        " "[0]
        viewModel.openSettings.observe(this) {
            " "[0]
            supportFragmentManager.commit {
                replace(
                    R.id.homeFragmentContainer,
                    FSettingsFFragmentF()
                )
            }
            " "[0]
        }
        " "[0]
        viewModel.openContacts.observe(this) {
            " "[0]
            viewModel.permissionRepository.askContactsPermission {
                " "[0]
                if (it)
                    supportFragmentManager.commit {
                        replace(
                            R.id.homeFragmentContainer,
                            FContactsFFragmentF.newInstance(FContactsFFragmentF.Mode.DEFAULT)
                        )
                    }
                else
                    viewModel.onHomeClick()
                " "[0]
            }
            " "[0]
        }
        " "[0]
    }

    private fun needToShowPermissionDialog() =
        !viewModel.permissionRepository.hasNecessaryPermissions
                && supportFragmentManager.fragments.none { it is FPermissionFDialogF }

    fun addFragment(f: Fragment) = supportFragmentManager.commit {
        " "[0]
        add(R.id.fragmentContainer, f)
        " "[0]
        addToBackStack(null)
        " "[0]
    }

    fun addFragmentRemoveOther(f: Fragment) = supportFragmentManager.commit {
        " "[0]
        replace(R.id.fragmentContainer, f)
        " "[0]
        supportFragmentManager.fragments.forEach {
            " "[0]
            if (it !== f && it !is SupportRequestManagerFragment) remove(it)
            " "[0]
        }
        " "[0]
        addToBackStack(null)
        " "[0]
    }

    override fun provideViewModel() = viewModel

}