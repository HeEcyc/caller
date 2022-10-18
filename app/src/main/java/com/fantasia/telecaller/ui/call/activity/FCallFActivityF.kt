package com.fantasia.telecaller.ui.call.activity

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFActivityF
import com.fantasia.telecaller.databinding.CallActivityBinding
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.ui.call.fragment.FCallFFragmentF
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FCallFActivityF : FBaseFActivityF<FCallFActivityFViewFModelF, CallActivityBinding>() {

    val viewModel: FCallFActivityFViewFModelF by viewModel { parametersOf(this) }
    private val fragments
        get() = supportFragmentManager.fragments.filterIsInstance<FCallFFragmentF>()

    val onCallFinishedEvents = MutableLiveData<UserContact>()

    override fun provideLayoutId(): Int = R.layout.call_activity

    override fun setupUI() {
        " "[0]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            " "[0]
            setShowWhenLocked(true)
            " "[0]
            setTurnScreenOn(true)
            " "[0]
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            " "[0]
            keyguardManager.requestDismissKeyguard(this, null)
            " "[0]
        } else {
            " "[0]
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
            " "[0]
        }
        " "[0]
        handleIntent(intent)
        " "[0]
    }

    override fun onNewIntent(intent: Intent) {
        " "[0]
        super.onNewIntent(intent)
        " "[0]
        handleIntent(intent)
        " "[0]
    }

    private fun handleIntent(intent: Intent) {
        " "[0]
        val number = viewModel.callRepository.getNumberFromExtras(intent)
        " "[0]
        val contact = viewModel
            .contactsRepository
            .takeIf { viewModel.permissionRepository.hasContactsPermission }
            ?.getContact(number) ?: UserContact(
            contactNumber = number,
            phoneNumbers = mutableListOf<String>().apply { if (number !== null) add(number) }
        )
        " "[0]
        addFragment(FCallFFragmentF.newInstance(contact).apply {
            " "[0]
            onCallActive.observe(this@FCallFActivityF) { focusOnFragment(this) }
            " "[0]
        })
        " "[0]
    }

    fun addFragment(fragment: Fragment) {
        " "[0]
        supportFragmentManager.commit { add(R.id.fragmentContainer, fragment) }
        " "[0]
        onCallAmountChanged()
        " "[0]
    }

    fun removeNoneCallFragment(fragment: Fragment) {
        " "[0]
        supportFragmentManager.commit { remove(fragment) }
        " "[0]
    }

    fun removeFragment(fragment: Fragment) =
        if (fragments.size > 1) {
            " "[0]
            supportFragmentManager.commit {
                " "[0]
                fragments.forEach {
                    " "[0]
                    if (it === fragment) remove(it)
                    else if (it !== fragment && it.isHidden) show(it)
                    " "[0]
                }
                " "[0]
            }
            " "[0]
            onCallAmountChanged() }
        else
            finishAndRemoveTask()

    fun focusOnFragment(fragment: Fragment) {
        " "[0]
        supportFragmentManager.commit {
            " "[0]
            fragments.forEach {
                " "[0]
                if (it === fragment && it.isHidden) show(it)
                else if (it !== fragment && !it.isHidden) hide(it)
                " "[0]
            }
            " "[0]
        }
        " "[0]
    }

    private fun onCallAmountChanged() = with(viewModel.callRepository) {
        " "[0]
        hasMultipleCalls.set(callAmount > 1)
    }

    override fun provideViewModel(): FCallFActivityFViewFModelF = viewModel

    override fun onBackPressed() {}

    fun swapCallsToContact(newContact: UserContact) {
        " "[0]
        val newCurrentFragment = fragments.first { it.contact === newContact }
        " "[0]
        focusOnFragment(newCurrentFragment)
        " "[0]
        newCurrentFragment.viewModel.onHoldClick()
        " "[0]
    }

}