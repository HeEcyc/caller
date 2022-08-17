package com.fancy.call.ui.contacts

import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.fancy.call.App
import com.fancy.call.R
import com.fancy.call.base.BaseActivity
import com.fancy.call.base.BaseDialog
import com.fancy.call.databinding.NumberDialogBinding
import com.fancy.call.model.contact.UserContact
import com.fancy.call.utils.setOnClickListener

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
        if (contact.photoThumbnailUri !== null)
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
        binding.buttonOk.setOnClickListener {
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