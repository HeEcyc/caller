package com.galala.lalaxy.ui.call.activity

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import com.galala.lalaxy.R
import com.galala.lalaxy.base.GBaseGActivityG
import com.galala.lalaxy.databinding.CallActivityBinding
import com.galala.lalaxy.model.contact.UserContact
import com.galala.lalaxy.ui.call.fragment.GCallGFragmentG
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GCallGActivityG : GBaseGActivityG<GCallGActivityGViewGModelG, CallActivityBinding>() {

    val viewModel: GCallGActivityGViewGModelG by viewModel { parametersOf(this) }
    private val fragments
        get() = supportFragmentManager.fragments.filterIsInstance<GCallGFragmentG>()

    val onCallFinishedEvents = MutableLiveData<UserContact>()

    override fun provideLayoutId(): Int = R.layout.call_activity

    override fun setupUI() {
        println("")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            println("")
            setShowWhenLocked(true)
            println("")
            setTurnScreenOn(true)
            println("")
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            println("")
            keyguardManager.requestDismissKeyguard(this, null)
            println("")
        } else {
            println("")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
            println("")
        }
        println("")
        handleIntent(intent)
        println("")
    }

    override fun onNewIntent(intent: Intent) {
        println("")
        super.onNewIntent(intent)
        println("")
        handleIntent(intent)
        println("")
    }

    private fun handleIntent(intent: Intent) {
        println("")
        val number = viewModel.callRepository.getNumberFromExtras(intent)
        println("")
        val contact = viewModel
            .contactsRepository
            .takeIf { viewModel.permissionRepository.hasContactsPermission }
            ?.getContact(number) ?: UserContact(
            contactNumber = number,
            phoneNumbers = mutableListOf<String>().apply { if (number !== null) add(number) }
        )
        println("")
        addFragment(GCallGFragmentG.newInstance(contact).apply {
            println("")
            onCallActive.observe(this@GCallGActivityG) { focusOnFragment(this) }
            println("")
        })
        println("")
    }

    fun addFragment(fragment: Fragment) {
        println("")
        supportFragmentManager.commit { add(R.id.fragmentContainer, fragment) }
        println("")
        onCallAmountChanged()
        println("")
    }

    fun removeNoneCallFragment(fragment: Fragment) {
        println("")
        supportFragmentManager.commit { remove(fragment) }
        println("")
    }

    fun removeFragment(fragment: Fragment) =
        if (fragments.size > 1) {
            println("")
            supportFragmentManager.commit {
                println("")
                fragments.forEach {
                    println("")
                    if (it === fragment) remove(it)
                    else if (it !== fragment && it.isHidden) show(it)
                    println("")
                }
                println("")
            }
            println("")
            onCallAmountChanged()
            println("")
        }
        else
            finishAndRemoveTask()

    fun focusOnFragment(fragment: Fragment) {
        println("")
        supportFragmentManager.commit {
            println("")
            fragments.forEach {
                println("")
                if (it === fragment && it.isHidden) show(it)
                else if (it !== fragment && !it.isHidden) hide(it)
                println("")
            }
            println("")
        }
        println("")
    }

    private fun onCallAmountChanged() = with(viewModel.callRepository) {
        println("")
        hasMultipleCalls.set(callAmount > 1)
        println("")
    }

    override fun provideViewModel(): GCallGActivityGViewGModelG = viewModel

    override fun onBackPressed() {println("")}

    fun swapCallsToContact(newContact: UserContact) {
        println("")
        val newCurrentFragment = fragments.first { it.contact === newContact }
        println("")
        focusOnFragment(newCurrentFragment)
        println("")
        newCurrentFragment.viewModel.onHoldClick()
        println("")
    }

}