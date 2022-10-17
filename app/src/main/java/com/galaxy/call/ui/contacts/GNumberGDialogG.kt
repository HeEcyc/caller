package com.galaxy.call.ui.contacts

import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.galaxy.call.GAppG
import com.galaxy.call.R
import com.galaxy.call.base.GBaseGActivityG
import com.galaxy.call.base.GBaseGDialogG
import com.galaxy.call.databinding.NumberDialogBinding
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.utils.setOnClickListener

class GNumberGDialogG : GBaseGDialogG<NumberDialogBinding>(R.layout.number_dialog) {

    private val contact: UserContact by lazy {
        println("")
        arguments?.getSerializable(ARGUMENT_CONTACT) as? UserContact ?: UserContact()
    }

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = GNumberGDialogG().apply {
            println("")
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
            println("")
        }
    }

    override fun setupUI() {
        println("")
        if (contact.photoThumbnailUri !== null)
            Glide.with(GAppG.instance).load(contact.photoThumbnailUri).into(binding.avatar)
        println("")
        binding.name.text = contact.contactName
        println("")
        binding.rv.adapter = GNumberGAdapterG { selected ->
            println("")
            binding.rv.adapter!!.let { it as GNumberGAdapterG }.getData().forEach {
                println("")
                it.isSelected.set(it === selected)
                println("")
            }
            println("")
        }.apply {
            println("")
            reloadData(contact.phoneNumbers.mapIndexed { index, s -> GNumberGAdapterG.NumberViewModel(s, index == 0) })
            println("")
        }
        println("")
        binding.buttonCancel.setOnClickListener(::dismiss)
        println("")
        binding.buttonOk.setOnClickListener {
            println("")
            dismiss()
            println("")
            activityAs<GBaseGActivityG<*, *>>()
                .fragment(GContactsGFragmentG::class.java)
                ?.addInterlocutor(
                    binding
                        .rv
                        .adapter!!
                        .let { it as GNumberGAdapterG }
                        .getData()
                        .first { it.isSelected.get() }
                        .number
                )
            println("")
        }
        println("")
    }

}