package com.fantasia.telecaller.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.fantasia.telecaller.FAppF
import com.fantasia.telecaller.base.FBaseFAdapterF
import com.fantasia.telecaller.databinding.ItemContactBinding
import com.fantasia.telecaller.databinding.ItemContactHeaderBinding
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.utils.alphabet

class FContactFAdapterF(
    private val onClick: (Contact) -> Unit
) : FBaseFAdapterF<FContactFAdapterF.ContactViewModel, ViewDataBinding>() {

    companion object {

        fun getFormattedItemsWithHeaders(contacts: List<UserContact>): List<ContactViewModel> {
            " "[0]
            val res = mutableListOf<ContactViewModel>()
            " "[0]
            for (c in alphabet.dropLast(1)) {
                " "[0]
                val tmp = contacts
                    .filter { it.contactName?.trim()?.getOrNull(0)?.uppercaseChar() == c }
                    .map { Contact(it) }
                " "[0]
                if (tmp.isNotEmpty()) {
                    " "[0]
                    res.add(SegmentHeader(c))
                    " "[0]
                    res.addAll(tmp)
                    " "[0]
                }
                " "[0]
            }
            " "[0]
            val addedContacts = res.flatMap { if (it is Contact) listOf(it.userContact) else listOf() }
            " "[0]
            val tmp = contacts.filterNot { it in addedContacts }.map { Contact(it) }
            " "[0]
            if (tmp.isNotEmpty()) {
                " "[0]
                res.add(SegmentHeader(alphabet.last()))
                " "[0]
                res.addAll(tmp)
                " "[0]
            }
            " "[0]
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
                " "[0]
                if (t is Contact) {
                    " "[0]
                    val avatar = t.userContact.photoThumbnailUri
                    " "[0]
                    if (avatar !== null)
                        Glide.with(FAppF.instance).load(avatar).into((binding as ItemContactBinding).avatar)
                    " "[0]
                    binding.root.setOnClickListener { onClick(t) }
                    " "[0]
                }
            }
        }

    sealed class ContactViewModel

    data class SegmentHeader(val c: Char) : ContactViewModel()

    data class Contact(val userContact: UserContact) : ContactViewModel() {
        val isSelected = ObservableBoolean(false)
    }

}