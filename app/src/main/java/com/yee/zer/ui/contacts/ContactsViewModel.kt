package com.yee.zer.ui.contacts

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.yee.zer.base.ActivityViewModel
import com.yee.zer.model.contact.UserContact
import com.yee.zer.repository.ContactsRepository
import com.yee.zer.repository.LocaleRepository
import com.yee.zer.repository.PermissionRepository

class ContactsViewModel(
    val mode: ContactsActivity.Mode,
    localeRepository: LocaleRepository,
    contactsRepository: ContactsRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository) {

    val addInterlocutor = MutableLiveData<String>()
    val closeFragment = MutableLiveData<Unit>()
    val hardReloadRV = MutableLiveData<Unit>()

    private val allContacts: List<ContactAdapter.Contact>
    val adapterContacts = ContactAdapter(::onContactClick)

    val selectedContacts get() = adapterContacts
        .getData()
        .filter { it.isSelected.get() }
        .map { it.userContact }

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
                    (it.userContact.contactName ?: it.userContact.contactNumber ?: "")
                        .contains(searchQuery.get() ?: "", true)
                }
            }.let(adapterContacts.getData()::addAll)
            hardReloadRV.postValue(Unit)
        }
    }

    private fun getFormattedItems(contacts: List<UserContact>) =
            ContactAdapter.getFormattedItems(contacts)

    private fun onContactClick(contact: ContactAdapter.Contact) = when (mode) {
        ContactsActivity.Mode.CONTACT_SELECTOR -> contact.isSelected.set(!contact.isSelected.get())
        ContactsActivity.Mode.INTERLOCUTOR_SELECTOR -> when (contact.userContact.phoneNumbers.size) {
            0 -> {}
            1 -> addInterlocutor.postValue(contact.userContact.contactNumber!!)
            else -> addInterlocutor.postValue(contact.userContact.phoneNumbers[0])
        }
    }

    fun onBackClick() {
        closeFragment.postValue(Unit)
    }

    fun onClearClick() = searchQuery.set("")

}