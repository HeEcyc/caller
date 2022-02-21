package com.iiooss.ccaallll.ui.call.dialog

import androidx.core.os.bundleOf
import com.iiooss.ccaallll.R
import com.iiooss.ccaallll.base.BaseActivity
import com.iiooss.ccaallll.base.BaseDialog
import com.iiooss.ccaallll.databinding.NumberDialogBinding
import com.iiooss.ccaallll.model.contact.UserContact
import com.iiooss.ccaallll.ui.contacts.ContactsFragment
import com.iiooss.ccaallll.utils.setOnClickListener

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