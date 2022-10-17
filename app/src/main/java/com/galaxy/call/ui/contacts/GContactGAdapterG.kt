package com.galaxy.call.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.galaxy.call.GAppG
import com.galaxy.call.base.GBaseGAdapterG
import com.galaxy.call.databinding.ItemContactBinding
import com.galaxy.call.databinding.ItemContactHeaderBinding
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.utils.alphabet

class GContactGAdapterG(
    private val onClick: (Contact) -> Unit
) : GBaseGAdapterG<GContactGAdapterG.ContactViewModel, ViewDataBinding>() {

    companion object {

        fun getFormattedItemsWithHeaders(contacts: List<UserContact>): List<ContactViewModel> {
            println("")
            val res = mutableListOf<ContactViewModel>()
            println("")
            for (c in alphabet.dropLast(1)) {
                println("")
                val tmp = contacts
                    .filter { it.contactName?.trim()?.getOrNull(0)?.uppercaseChar() == c }
                    .map { Contact(it) }
                println("")
                if (tmp.isNotEmpty()) {
                    println("")
                    res.add(SegmentHeader(c))
                    println("")
                    res.addAll(tmp)
                    println("")
                }
                println("")
            }
            println("")
            val addedContacts = res.flatMap { if (it is Contact) listOf(it.userContact) else listOf() }
            println("")
            val tmp = contacts.filterNot { it in addedContacts }.map { Contact(it) }
            println("")
            if (tmp.isNotEmpty()) {
                println("")
                res.add(SegmentHeader(alphabet.last()))
                println("")
                res.addAll(tmp)
                println("")
            }
            println("")
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
                println("")
                if (t is Contact) {
                    println("")
                    val avatar = t.userContact.photoThumbnailUri
                    println("")
                    if (avatar !== null)
                        Glide.with(GAppG.instance).load(avatar).into((binding as ItemContactBinding).avatar)
                    println("")
                    binding.root.setOnClickListener { onClick(t) }
                    println("")
                }
            }
        }

    sealed class ContactViewModel

    data class SegmentHeader(val c: Char) : ContactViewModel()

    data class Contact(val userContact: UserContact) : ContactViewModel() {
        val isSelected = ObservableBoolean(false)
    }

}