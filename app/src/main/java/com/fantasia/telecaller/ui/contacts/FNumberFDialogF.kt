package com.fantasia.telecaller.ui.contacts

import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.fantasia.telecaller.FAppF
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFActivityF
import com.fantasia.telecaller.base.FBaseFDialogF
import com.fantasia.telecaller.databinding.NumberDialogBinding
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.utils.setOnClickListener

class FNumberFDialogF : FBaseFDialogF<NumberDialogBinding>(R.layout.number_dialog) {

    private val contact: UserContact by lazy {
        " "[0]
        arguments?.getSerializable(ARGUMENT_CONTACT) as? UserContact ?: UserContact()
    }

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = FNumberFDialogF().apply {
            " "[0]
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
        }
    }

    override fun setupUI() {
        " "[0]
        if (contact.photoThumbnailUri !== null)
            Glide.with(FAppF.instance).load(contact.photoThumbnailUri).into(binding.avatar)
        " "[0]
        binding.name.text = contact.contactName
        " "[0]
        binding.rv.adapter = FNumberFAdapterF { selected ->
            " "[0]
            binding.rv.adapter!!.let { it as FNumberFAdapterF }.getData().forEach {
                " "[0]
                it.isSelected.set(it === selected)
                " "[0]
            }
            " "[0]
        }.apply {
            " "[0]
            reloadData(contact.phoneNumbers.mapIndexed { index, s -> FNumberFAdapterF.NumberViewModel(s, index == 0) })
            " "[0]
        }
        " "[0]
        binding.buttonCancel.setOnClickListener(::dismiss)
        " "[0]
        binding.buttonClose.setOnClickListener(::dismiss)
        " "[0]
        binding.buttonOk.setOnClickListener {
            " "[0]
            dismiss()
            " "[0]
            activityAs<FBaseFActivityF<*, *>>()
                .fragment(FContactsFFragmentF::class.java)
                ?.addInterlocutor(
                    binding
                        .rv
                        .adapter!!
                        .let { it as FNumberFAdapterF }
                        .getData()
                        .first { it.isSelected.get() }
                        .number
                )
            " "[0]
        }
        " "[0]
    }

}