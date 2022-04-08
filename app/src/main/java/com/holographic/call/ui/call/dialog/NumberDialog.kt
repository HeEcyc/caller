package com.holographic.call.ui.call.dialog

import androidx.core.os.bundleOf
import com.holographic.call.R
import com.holographic.call.base.BaseActivity
import com.holographic.call.base.BaseDialog
import com.holographic.call.databinding.NumberDialogBinding
import com.holographic.call.model.contact.UserContact
import com.holographic.call.ui.contacts.ContactsFragment
import com.holographic.call.utils.setOnClickListener

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
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}