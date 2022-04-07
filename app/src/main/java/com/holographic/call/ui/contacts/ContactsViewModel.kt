package com.holographic.call.ui.contacts

import androidx.lifecycle.MutableLiveData
import com.holographic.call.base.BaseViewModel
import com.holographic.call.model.contact.UserContact
import com.holographic.call.repository.ContactsRepository
import com.holographic.call.repository.LocaleRepository
import com.holographic.call.repository.PermissionRepository
import com.holographic.call.utils.alphabet

class ContactsViewModel(
    val mode: ContactsFragment.Mode,
    val localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    private val locale = localeRepository.locale ?: LocaleRepository.Locale.ENGLISH

    val onContactSelected = MutableLiveData<UserContact>()
    val openContact = MutableLiveData<UserContact>()
    val addInterlocutor = MutableLiveData<String>()
    val selectInterlocutorNumber = MutableLiveData<UserContact>()

    val adapterContacts = ContactAdapter(::onContactClick)
    val adapterAlphabet = if (locale == LocaleRepository.Locale.ENGLISH)
        AlphabetAdapter()
    else
        null

    init {
        val contacts = if (locale == LocaleRepository.Locale.ENGLISH)
            ContactAdapter.getFormattedItemsWithHeaders(contactsRepository.contacts)
        else
            ContactAdapter.getFormattedItemsWithoutHeaders(contactsRepository.contacts)
        adapterContacts.reloadData(contacts)
        adapterAlphabet?.reloadData(alphabet)
    }

    private fun onContactClick(contact: UserContact) = when (mode) {
        ContactsFragment.Mode.DEFAULT -> openContact.postValue(contact)
        ContactsFragment.Mode.CONTACT_SELECTOR -> onContactSelected.postValue(contact)
        ContactsFragment.Mode.INTERLOCUTOR_SELECTOR -> when (contact.phoneNumbers.size) {
            0 -> {}
            1 -> addInterlocutor.postValue(contact.contactNumber!!)
            else -> selectInterlocutorNumber.postValue(contact)
        }

    }

}