package com.roobcall.themes.ui.contacts

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.roobcall.themes.base.BaseViewModel
import com.roobcall.themes.model.contact.UserContact
import com.roobcall.themes.repository.ContactsRepository
import com.roobcall.themes.repository.LocaleRepository
import com.roobcall.themes.repository.PermissionRepository

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
    val closeFragment = MutableLiveData<Unit>()
    val hardReloadRV = MutableLiveData<Unit>()

    private val allContacts: List<ContactAdapter.ContactViewModel>
    val adapterContacts = ContactAdapter(::onContactClick)

    val selectedContacts get() = adapterContacts
        .getData()
        .mapNotNull {
            (it as? ContactAdapter.Contact)?.let {
                c -> c.userContact.takeIf { c.isSelected.get() }
            }
        }

    val isSearching = ObservableBoolean(false)
    val searchQuery = ObservableField("")

    init {
        val contacts = getFormattedItems(contactsRepository.contacts)
        allContacts = contacts
        adapterContacts.reloadData(contacts)
        observe(searchQuery) { _, _ ->
            adapterContacts.getData().clear()
            if (searchQuery.get()?.trim()?.isEmpty() == true)
                allContacts
            else {
                allContacts.filter {
                    it is ContactAdapter.Contact &&
                            (it.userContact.contactName ?: it.userContact.contactNumber ?: "")
                                .contains(searchQuery.get() ?: "", true)
                }
            }.let(adapterContacts.getData()::addAll)
            hardReloadRV.postValue(Unit)
        }
    }

    private fun getFormattedItems(contacts: List<UserContact>) =
        if (locale == LocaleRepository.Locale.ENGLISH || locale == LocaleRepository.Locale.RUSSIAN)
            ContactAdapter.getFormattedItemsWithHeaders(contacts)
        else
            ContactAdapter.getFormattedItemsWithoutHeaders(contacts)

    private fun onContactClick(contact: ContactAdapter.Contact) = when (mode) {
        ContactsFragment.Mode.DEFAULT -> openContact.postValue(contact.userContact)
        ContactsFragment.Mode.CONTACT_SELECTOR -> contact.isSelected.set(!contact.isSelected.get())
        ContactsFragment.Mode.INTERLOCUTOR_SELECTOR -> when (contact.userContact.phoneNumbers.size) {
            0 -> {}
            1 -> addInterlocutor.postValue(contact.userContact.contactNumber!!)
            else -> selectInterlocutorNumber.postValue(contact.userContact)
        }
    }

    fun onBackClick() {
        if (isSearching.get()) {
            isSearching.set(false)
            searchQuery.set("")
        } else closeFragment.postValue(Unit)
    }

    fun onSearchClick() = isSearching.set(true)

    fun onClearClick() = searchQuery.set("")

}