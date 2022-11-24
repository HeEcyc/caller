package com.vefercal.ler.ui.contacts

import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.vefercal.ler.App
import com.vefercal.ler.R
import com.vefercal.ler.base.BaseActivity
import com.vefercal.ler.base.BaseDialog
import com.vefercal.ler.databinding.NumberDialogBinding
import com.vefercal.ler.model.contact.UserContact
import com.vefercal.ler.utils.setOnClickListener

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
        binding.buttonCancel.setOnClickListener(::dismiss)
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}