package com.iiooss.ccaallll.ui.contacts

import androidx.lifecycle.MutableLiveData
import com.iiooss.ccaallll.base.BaseViewModel
import com.iiooss.ccaallll.model.contact.UserContact
import com.iiooss.ccaallll.repository.ContactsRepository
import com.iiooss.ccaallll.repository.LocaleRepository
import com.iiooss.ccaallll.utils.alphabet

class ContactsViewModel(
    val mode: ContactsFragment.Mode,
    val localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository
) : BaseViewModel() {

    private val locale = localeRepository.locale ?: LocaleRepository.Locale.ENGLISH

    val onContactSelected = MutableLiveData<UserContact>()
    val openContact = MutableLiveData<UserContact>()

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
        ContactsFragment.Mode.INTERLOCUTOR_SELECTOR -> {}//todo
    }

}