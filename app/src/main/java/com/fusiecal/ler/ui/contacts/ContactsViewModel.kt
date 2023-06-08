package com.fusiecal.ler.ui.contacts

import androidx.lifecycle.MutableLiveData
import com.fusiecal.ler.base.ActivityViewModel
import com.fusiecal.ler.model.contact.UserContact
import com.fusiecal.ler.repository.ContactsRepository
import com.fusiecal.ler.repository.LocaleRepository
import com.fusiecal.ler.repository.PermissionRepository

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