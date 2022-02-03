package com.callerafter.lovelycall.ui.contacts.number

import androidx.core.os.bundleOf
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseActivity
import com.callerafter.lovelycall.base.BaseDialog
import com.callerafter.lovelycall.databinding.NumberDialogBinding
import com.callerafter.lovelycall.model.contact.UserContact
import com.callerafter.lovelycall.ui.contacts.ContactsFragment
import com.callerafter.lovelycall.utils.setOnClickListener

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
        binding.textName.text = contact.contactName
        val adapter = NumberAdapter {
            dismiss()
            activityAs<BaseActivity<*, *>>().fragment(ContactsFragment::class.java)?.addInterlocutor(it)
        }
        adapter.reloadData(contact.phoneNumbers)
        binding.shadowRecycler.adapter = adapter
        binding.recycler.adapter = adapter
        binding.buttonCancel.setOnClickListener(::dismiss)
    }

}