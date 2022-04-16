package com.maxios.maxcall.ui.contacts

import androidx.core.os.bundleOf
import com.maxios.maxcall.R
import com.maxios.maxcall.base.BaseActivity
import com.maxios.maxcall.base.BaseDialog
import com.maxios.maxcall.databinding.NumberDialogBinding
import com.maxios.maxcall.model.contact.UserContact

class NumberDialog : BaseDialog<NumberDialogBinding>(R.layout.number_dialog) {

    private val contact: UserContact by lazy {
        arguments?.getSerializable(ARGUMENT_CONTACT) as? UserContact ?: UserContact()
    }

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = NumberDialog().apply {
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
        }
    }

    override fun setupUI() {
        binding.contactName.text = contact.contactName
        val adapter = NumberAdapter {
            dismiss()
            activityAs<BaseActivity<*, *>>().fragment(ContactsFragment::class.java)?.addInterlocutor(it)
        }
        adapter.reloadData(contact.phoneNumbers)
        binding.recycler.adapter = adapter
    }

}