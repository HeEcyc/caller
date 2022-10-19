package com.glasserino.caller.ui.contacts.dialog

import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.glasserino.caller.App
import com.glasserino.caller.R
import com.glasserino.caller.base.BaseActivity
import com.glasserino.caller.base.BaseDialog
import com.glasserino.caller.databinding.NumberDialogBinding
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.ui.contacts.ContactsFragment
import com.glasserino.caller.utils.setOnClickListener

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
        Glide.with(App.instance).load(contact.photoThumbnailUri).into(binding.avatar)
        binding.name.text = contact.contactName
        binding.rv.adapter = NumberAdapter { selected ->
            binding.rv.adapter!!.let { it as NumberAdapter }.getData().forEach {
                it.isSelected.set(it === selected)
            }
        }.apply {
            reloadData(contact.phoneNumbers.mapIndexed { index, s -> NumberAdapter.NumberViewModel(s, index == 0) })
        }
        binding.buttonCancel.setOnClickListener(::dismiss)
        binding.buttonSelect.setOnClickListener {
            dismiss()
            activityAs<BaseActivity<*, *>>()
                .fragment(ContactsFragment::class.java)
                ?.addInterlocutor(
                    binding
                        .rv
                        .adapter!!
                        .let { it as NumberAdapter }
                        .getData()
                        .first { it.isSelected.get() }
                        .number
                )
        }
    }

}