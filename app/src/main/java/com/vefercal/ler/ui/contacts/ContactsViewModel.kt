package com.vefercal.ler.ui.contacts

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.vefercal.ler.base.BaseViewModel
import com.vefercal.ler.model.contact.UserContact
import com.vefercal.ler.repository.ContactsRepository
import com.vefercal.ler.repository.LocaleRepository
import com.vefercal.ler.repository.PermissionRepository

class ContactsViewModel(
    val mode: ContactsFragment.Mode,
    val localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    val openContact = MutableLiveData<UserContact>()
    val addInterlocutor = MutableLiveData<String>()
    val selectInterlocutorNumber = MutableLiveData<UserContact>()
    val closeFragment = MutableLiveData<Unit>()
    val hardReloadRV = MutableLiveData<Unit>()

    private val allContacts: List<ContactAdapter.Contact>
    val adapterContacts = ContactAdapter(::onContactClick)

    val selectedContacts get() = adapterContacts
        .getData()
        .filter { it.isSelected.get() }
        .map { it.userContact }

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