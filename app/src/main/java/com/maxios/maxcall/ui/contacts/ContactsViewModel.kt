package com.maxios.maxcall.ui.contacts

import androidx.lifecycle.MutableLiveData
import com.maxios.maxcall.base.BaseViewModel
import com.maxios.maxcall.model.contact.UserContact
import com.maxios.maxcall.repository.ContactsRepository
import com.maxios.maxcall.repository.LocaleRepository
import com.maxios.maxcall.repository.PermissionRepository

class ContactsViewModel(
    val mode: ContactsFragment.Mode,
    val localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    private val locale = localeRepository.locale ?: LocaleRepository.Locale.ENGLISH

    val openContact = MutableLiveData<UserContact>()
    val addInterlocutor = MutableLiveData<String>()
    val selectInterlocutorNumber = MutableLiveData<UserContact>()

    val adapterContacts = ContactAdapter(::onContactClick)

    val selectedContacts get() = adapterContacts
        .getData()
        .filter { it is ContactAdapter.Contact && it.isSelected.get() }
        .map { (it as ContactAdapter.Contact).userContact }

    init {
        val contacts = if (locale == LocaleRepository.Locale.ENGLISH || locale == LocaleRepository.Locale.RUSSIAN)
            ContactAdapter.getFormattedItemsWithHeaders(contactsRepository.contacts)
        else
            ContactAdapter.getFormattedItemsWithoutHeaders(contactsRepository.contacts)
        adapterContacts.reloadData(contacts)
    }

    private fun onContactClick(contact: ContactAdapter.Contact) = when (mode) {
        ContactsFragment.Mode.DEFAULT -> openContact.postValue(contact.userContact)
        ContactsFragment.Mode.CONTACT_SELECTOR -> contact.isSelected.set(!contact.isSelected.get())
        ContactsFragment.Mode.INTERLOCUTOR_SELECTOR -> when (contact.userContact.phoneNumbers.size) {
            0 -> {}
            1 -> addInterlocutor.postValue(contact.userContact.contactNumber!!)
            else -> selectInterlocutorNumber.postValue(contact.userContact)
        }

    }

}