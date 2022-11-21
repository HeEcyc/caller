package com.roobcall.themes.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.roobcall.themes.App
import com.roobcall.themes.base.BaseAdapter
import com.roobcall.themes.databinding.ItemContactBinding
import com.roobcall.themes.databinding.ItemContactHeaderBinding
import com.roobcall.themes.model.contact.UserContact
import com.roobcall.themes.utils.alphabet

class ContactAdapter(
    private val onClick: (Contact) -> Unit
) : BaseAdapter<ContactAdapter.ContactViewModel, ViewDataBinding>() {

    companion object {

        fun getFormattedItemsWithHeaders(contacts: List<UserContact>): List<ContactViewModel> {
            val res = mutableListOf<ContactViewModel>()
            for (c in alphabet.dropLast(1)) {
                val tmp = contacts
                    .filter { it.contactName?.trim()?.getOrNull(0)?.uppercaseChar() == c }
                    .map { Contact(it) }
                if (tmp.isNotEmpty()) {
                    res.add(SegmentHeader(c))
                    res.addAll(tmp)
                }
            }
            val addedContacts = res.flatMap { if (it is Contact) listOf(it.userContact) else listOf() }
            val tmp = contacts.filterNot { it in addedContacts }.map { Contact(it) }
            if (tmp.isNotEmpty()) {
                res.add(SegmentHeader(alphabet.last()))
                res.addAll(tmp)
            }
            return res
        }

        fun getFormattedItemsWithoutHeaders(contacts: List<UserContact>): List<ContactViewModel> =
            contacts.map { Contact(it) }

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = if (getData()[item] is Contact)
        ItemContactBinding.inflate(inflater, viewGroup, false)
    else
        ItemContactHeaderBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ViewDataBinding) =
        object : BaseItem<ContactViewModel, ViewDataBinding>(binding) {
            override fun bind(t: ContactViewModel) {
                if (t is Contact) {
                    (binding as ItemContactBinding).textAvatar.text = t.userContact.contactName?.substring(0..0)
                    val avatar = t.userContact.photoThumbnailUri
                    if (avatar !== null)
                        Glide.with(App.instance).load(avatar).into(binding.avatar)
                    binding.root.setOnClickListener { onClick(t) }
                }
            }
        }

    sealed class ContactViewModel

    data class SegmentHeader(val c: Char) : ContactViewModel()

    data class Contact(val userContact: UserContact) : ContactViewModel() {
        val isSelected = ObservableBoolean(false)
    }

}
