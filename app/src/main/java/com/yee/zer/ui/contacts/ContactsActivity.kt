package com.yee.zer.ui.contacts

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.yee.zer.R
import com.yee.zer.base.BaseActivity
import com.yee.zer.databinding.ContactsActivityBinding
import com.yee.zer.model.contact.UserContact
import com.yee.zer.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ContactsActivity : BaseActivity<ContactsViewModel, ContactsActivityBinding>() {

    val viewModel: ContactsViewModel by viewModel { parametersOf(mode, this) }

    private val mode: Mode by lazy { intent?.getSerializableExtra(ARGUMENT_MODE) as? Mode ?: Mode.CONTACT_SELECTOR }

    companion object {
        const val EXTRAS_CONTACTS = "extras_contacts"

        private const val ARGUMENT_MODE = "argument_mode"

        fun newIntent(context: Context) =
            Intent(context, ContactsActivity::class.java)
                .putExtra(ARGUMENT_MODE, Mode.CONTACT_SELECTOR)

        fun newIntent(context: Context, mode: Mode) =
            Intent(context, ContactsActivity::class.java)
                .putExtra(ARGUMENT_MODE, mode)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        binding.buttonApply.setOnClickListener {
            applyThemeToContacts(viewModel.selectedContacts)
        }
        binding.buttonCancel.setOnClickListener(::finish)
        viewModel.addInterlocutor.observe(this, ::addInterlocutor)
        viewModel.closeFragment.observe(this) { onBackPressed() }
        viewModel.hardReloadRV.observe(this) {
            binding.rv.adapter = viewModel.adapterContacts
        }
    }

    private fun addInterlocutor(number: String) {
        viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
            if (it) call(number)
            if (mode == Mode.INTERLOCUTOR_SELECTOR) finish()
        }
    }

    private fun applyThemeToContacts(contacts: List<UserContact>) {
        setResult(RESULT_OK, Intent().putExtra(EXTRAS_CONTACTS, contacts.toTypedArray()))
        finish()
    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        CONTACT_SELECTOR, INTERLOCUTOR_SELECTOR
    }

    override fun provideLayoutId() = R.layout.contacts_activity

}