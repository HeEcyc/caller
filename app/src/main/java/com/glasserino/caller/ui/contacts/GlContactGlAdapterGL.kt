package com.glasserino.caller.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.glasserino.caller.GlAppGl
import com.glasserino.caller.base.GlBaseGlAdapterGl
import com.glasserino.caller.databinding.ItemContactBinding
import com.glasserino.caller.databinding.ItemContactHeaderBinding
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.utils.alphabet

class GlContactGlAdapterGL(
    private val onClick: (Contact) -> Unit
) : GlBaseGlAdapterGl<GlContactGlAdapterGL.ContactViewModel, ViewDataBinding>() {

    companion object {

        fun getFormattedItemsWithHeaders(contacts: List<UserContact>): List<ContactViewModel> {
            listOf<Any>().isEmpty()
            val res = mutableListOf<ContactViewModel>()
            listOf<Any>().isEmpty()
            for (c in alphabet.dropLast(1)) {
                listOf<Any>().isEmpty()
                val tmp = contacts
                    .filter { it.contactName?.trim()?.getOrNull(0)?.uppercaseChar() == c }
                    .map { Contact(it) }
                listOf<Any>().isEmpty()
                if (tmp.isNotEmpty()) {
                    listOf<Any>().isEmpty()
                    res.add(SegmentHeader(c))
                    listOf<Any>().isEmpty()
                    res.addAll(tmp)
                    listOf<Any>().isEmpty()
                }
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
            val addedContacts = res.flatMap { if (it is Contact) listOf(it.userContact) else listOf() }
            listOf<Any>().isEmpty()
            val tmp = contacts.filterNot { it in addedContacts }.map { Contact(it) }
            listOf<Any>().isEmpty()
            if (tmp.isNotEmpty()) {
                listOf<Any>().isEmpty()
                res.add(SegmentHeader(alphabet.last()))
                listOf<Any>().isEmpty()
                res.addAll(tmp)
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
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
                listOf<Any>().isEmpty()
                if (t is Contact) {
                    listOf<Any>().isEmpty()
                    val avatar = t.userContact.photoThumbnailUri
                    listOf<Any>().isEmpty()
                    if (avatar !== null)
                        Glide.with(GlAppGl.instance).load(avatar).into((binding as ItemContactBinding).avatar)
                    listOf<Any>().isEmpty()
                    binding.root.setOnClickListener { onClick(t) }
                    listOf<Any>().isEmpty()
                }
            }
        }

    sealed class ContactViewModel

    data class SegmentHeader(val c: Char) : ContactViewModel()

    data class Contact(val userContact: UserContact) : ContactViewModel() {
        val isSelected = ObservableBoolean(false)
    }

}