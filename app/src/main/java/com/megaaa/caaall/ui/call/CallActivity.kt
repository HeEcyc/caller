package com.megaaa.caaall.ui.call

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telecom.Call
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseActivity
import com.megaaa.caaall.databinding.CallActivityBinding
import com.megaaa.caaall.model.contact.UserContact
import com.megaaa.caaall.repository.PermissionRepository
import com.megaaa.caaall.ui.call.dialog.ActiveCallAdapter
import com.megaaa.caaall.ui.call.dialog.ActiveCallDialog
import com.megaaa.caaall.ui.call.fragment.CallFragment
import org.koin.android.viewmodel.ext.android.viewModel

class CallActivity : BaseActivity<CallActivityViewModel, CallActivityBinding>() {

    val viewModel: CallActivityViewModel by viewModel()
    private val fragments
        get() = supportFragmentManager.fragments.filterIsInstance<CallFragment>()

    val onCallFinishedEvents = MutableLiveData<UserContact>()

    var callSwapDialog: ActiveCallDialog? = null

    override fun provideLayoutId(): Int = R.layout.call_activity

    override fun setupUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
        viewModel.permissionRepository = PermissionRepository(this)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        callSwapDialog?.dismiss()
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val number = viewModel.callRepository.getNumberFromExtras(intent)
        val contact = viewModel
            .contactsRepository
            .takeIf { viewModel.permissionRepository.hasContactsPermission }
            ?.getContact(number) ?: UserContact(
                contactNumber = number,
                phoneNumbers = mutableListOf<String>().apply { if (number !== null) add(number) }
            )
        addFragment(CallFragment.newInstance(contact).apply {
            onCallActive.observe(this@CallActivity) { focusOnFragment(this) }
        })
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.commit { add(R.id.fragmentContainer, fragment) }
        onCallAmountChanged()
    }

    fun removeNoneCallFragment(fragment: Fragment) {
        supportFragmentManager.commit { remove(fragment) }
    }

    fun removeFragment(fragment: Fragment) =
        if (fragments.size > 1) {
            supportFragmentManager.commit {
                fragments.forEach {
                    if (it === fragment) remove(it)
                    else if (it !== fragment && it.isHidden) show(it)
                }
            }
            onCallAmountChanged()
        }
        else
            finishAndRemoveTask()

    fun focusOnFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            fragments.forEach {
                if (it === fragment && it.isHidden) show(it)
                else if (it !== fragment && !it.isHidden) hide(it)
            }
        }
    }

    private fun onCallAmountChanged() = with(viewModel.callRepository) {
        hasMultipleCalls.set(callAmount > 1)
    }

    override fun provideViewModel(): CallActivityViewModel = viewModel

    override fun onBackPressed() {}

    fun getActiveCallViewModelItems() = fragments.map {
        ActiveCallAdapter.ActiveCallViewModel(
            it.contact,
            it.contact === fragments.firstOrNull {
                fragment -> fragment.viewModel.callState.value == Call.STATE_ACTIVE
            }?.contact
        )
    }

    fun swapCallsToContact(newContact: UserContact) {
        val newCurrentFragment = fragments.first { it.contact === newContact }
        focusOnFragment(newCurrentFragment)
        newCurrentFragment.viewModel.onHoldClick()
    }

}