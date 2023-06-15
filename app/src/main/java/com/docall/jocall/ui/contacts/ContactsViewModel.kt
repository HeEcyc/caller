package com.docall.jocall.ui.contacts

import androidx.lifecycle.MutableLiveData
import com.docall.jocall.base.ActivityViewModel
import com.docall.jocall.model.contact.UserContact
import com.docall.jocall.repository.ContactsRepository
import com.docall.jocall.repository.LocaleRepository
import com.docall.jocall.repository.PermissionRepository

class ContactsViewModel(
    val mode: ContactsActivity.Mode,
    localeRepository: LocaleRepository,
    contactsRepository: ContactsRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository) {

    val closeFragment = MutableLiveData<Unit>()

    private val allContacts: List<ContactAdapter.Contact>
    val adapterContacts = ContactAdapter(::onContactClick)

    init {
        val contacts = getFormattedItems(contactsRepository.contacts)
        allContacts = contacts
        adapterContacts.reloadData(contacts)
    }

    private fun getFormattedItems(contacts: List<UserContact>) =
            ContactAdapter.getFormattedItems(contacts)

    private fun onContactClick(contact: ContactAdapter.Contact): Unit =
        adapterContacts.getData().forEach { it.isSelected.set(it === contact) }

    fun onBackClick() {
        closeFragment.postValue(Unit)
    }

}