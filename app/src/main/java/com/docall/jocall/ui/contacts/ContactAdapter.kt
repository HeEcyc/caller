package com.docall.jocall.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import com.docall.jocall.App
import com.docall.jocall.base.BaseAdapter
import com.docall.jocall.databinding.ItemContactBinding
import com.docall.jocall.model.contact.UserContact

class ContactAdapter(
    private val onClick: (Contact) -> Unit
) : BaseAdapter<ContactAdapter.Contact, ItemContactBinding>() {

    companion object {

        fun getFormattedItems(contacts: List<UserContact>): List<Contact> =
            contacts.map { Contact(it) }

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemContactBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemContactBinding) =
        object : BaseItem<Contact, ItemContactBinding>(binding) {
            override fun bind(t: Contact) {
                val avatar = t.userContact.photoThumbnailUri
                if (avatar !== null) {
                    Glide.with(App.instance).load(avatar).into(binding.avatar)
                }
                binding.letter.text = t.userContact.contactName?.trim()?.firstOrNull()?.toString()
                binding.root.setOnClickListener { onClick(t) }
            }
        }

    data class Contact(val userContact: UserContact) {
        val isSelected = ObservableBoolean(false)
    }

}