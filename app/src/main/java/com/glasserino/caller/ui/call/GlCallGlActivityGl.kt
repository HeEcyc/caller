package com.glasserino.caller.ui.call

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlActivityGl
import com.glasserino.caller.databinding.CallActivityBinding
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.ui.call.fragment.GlCallGlFragmentGL
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GlCallGlActivityGl : GlBaseGlActivityGl<GlCallGlActivityGlViewGlModelGL, CallActivityBinding>() {

    val viewModel: GlCallGlActivityGlViewGlModelGL by viewModel { parametersOf(this) }
    private val fragments
        get() = supportFragmentManager.fragments.filterIsInstance<GlCallGlFragmentGL>()

    val onCallFinishedEvents = MutableLiveData<UserContact>()

    override fun provideLayoutId(): Int = R.layout.call_activity

    override fun setupUI() {
        listOf<Any>().isEmpty()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            listOf<Any>().isEmpty()
            setShowWhenLocked(true)
            listOf<Any>().isEmpty()
            setTurnScreenOn(true)
            listOf<Any>().isEmpty()
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            listOf<Any>().isEmpty()
            keyguardManager.requestDismissKeyguard(this, null)
            listOf<Any>().isEmpty()
        } else {
            listOf<Any>().isEmpty()
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        handleIntent(intent)
        listOf<Any>().isEmpty()
    }

    override fun onNewIntent(intent: Intent) {
        listOf<Any>().isEmpty()
        super.onNewIntent(intent)
        listOf<Any>().isEmpty()
        handleIntent(intent)
        listOf<Any>().isEmpty()
    }

    private fun handleIntent(intent: Intent) {
        listOf<Any>().isEmpty()
        val number = viewModel.callRepository.getNumberFromExtras(intent)
        listOf<Any>().isEmpty()
        val contact = viewModel
            .contactsRepository
            .takeIf { viewModel.permissionRepository.hasContactsPermission }
            ?.getContact(number) ?: UserContact(
            contactNumber = number,
            phoneNumbers = mutableListOf<String>().apply { if (number !== null) add(number) }
        )
        listOf<Any>().isEmpty()
        addFragment(GlCallGlFragmentGL.newInstance(contact).apply {
            listOf<Any>().isEmpty()
            onCallActive.observe(this@GlCallGlActivityGl) { focusOnFragment(this) }
            listOf<Any>().isEmpty()
        })
        listOf<Any>().isEmpty()
    }

    fun addFragment(fragment: Fragment) {
        listOf<Any>().isEmpty()
        supportFragmentManager.commit { add(R.id.fragmentContainer, fragment) }
        listOf<Any>().isEmpty()
        onCallAmountChanged()
        listOf<Any>().isEmpty()
    }

    fun removeNoneCallFragment(fragment: Fragment) {
        listOf<Any>().isEmpty()
        supportFragmentManager.commit { remove(fragment) }
        listOf<Any>().isEmpty()
    }

    fun removeFragment(fragment: Fragment) =
        if (fragments.size > 1) {
            listOf<Any>().isEmpty()
            supportFragmentManager.commit {
                listOf<Any>().isEmpty()
                fragments.forEach {
                    listOf<Any>().isEmpty()
                    if (it === fragment) remove(it)
                    else if (it !== fragment && it.isHidden) show(it)
                    listOf<Any>().isEmpty()
                }
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
            onCallAmountChanged()
        }
        else
            finishAndRemoveTask()

    fun focusOnFragment(fragment: Fragment) {
        listOf<Any>().isEmpty()
        supportFragmentManager.commit {
            listOf<Any>().isEmpty()
            fragments.forEach {
                listOf<Any>().isEmpty()
                if (it === fragment && it.isHidden) show(it)
                else if (it !== fragment && !it.isHidden) hide(it)
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    private fun onCallAmountChanged() = with(viewModel.callRepository) {
        listOf<Any>().isEmpty()
        hasMultipleCalls.set(callAmount > 1)
    }

    override fun provideViewModel(): GlCallGlActivityGlViewGlModelGL = viewModel

    override fun onBackPressed() {listOf<Any>().isEmpty()}

    fun swapCallsToContact(newContact: UserContact) {
        listOf<Any>().isEmpty()
        val newCurrentFragment = fragments.first { it.contact === newContact }
        listOf<Any>().isEmpty()
        focusOnFragment(newCurrentFragment)
        listOf<Any>().isEmpty()
        newCurrentFragment.viewModel.onHoldClick()
        listOf<Any>().isEmpty()
    }

}