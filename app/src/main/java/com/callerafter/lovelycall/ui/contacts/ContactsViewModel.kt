package com.callerafter.lovelycall.ui.contacts

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.callerafter.lovelycall.base.BaseViewModel
import com.callerafter.lovelycall.model.contact.UserContact
import com.callerafter.lovelycall.model.contact.UserContactViewModel
import com.callerafter.lovelycall.repository.ContactsRepository
import com.callerafter.lovelycall.repository.PermissionRepository
import com.callerafter.lovelycall.ui.contacts.ContactsFragment.Mode
import com.callerafter.lovelycall.ui.contacts.ContactsFragment.Mode.*

class ContactsViewModel(
    val mode: Mode,
    private val contactsRepository: ContactsRepository
) : BaseViewModel() {

    lateinit var permissionRepository: PermissionRepository

    val onBackClickEvents = MutableLiveData<Unit>()
    val selectedContacts = MutableLiveData<List<UserContact>>()
    val addInterlocutor = MutableLiveData<String>()
    val selectInterlocutorNumber = MutableLiveData<UserContact>()
    val openContact = MutableLiveData<UserContact>()

    val searchQuery = ObservableField("")
    val isSearching = ObservableBoolean(false)
    val hasSelectedItems = ObservableBoolean(false)

    private val allContacts = contactsRepository.contacts.map { UserContactViewModel(it) }
    val adapter = ContactsAdapter(::onContactClick)

    init {
        adapter.reloadData(allContacts)
        observe(searchQuery) { _, _ ->
            adapter.reloadData(getSearchQueryResult(searchQuery.get()))
        }
    }

    private fun getSearchQueryResult(query: String?) = if (query?.length ?: 0 > 0)
        allContacts.filter { vm ->
            vm.contact
                .contactName
                ?.split(" ")
                ?.any { it.startsWith(query!!, true) } ?: false
        }
    else
        allContacts

    private fun onContactClick(contact: UserContactViewModel) = when (mode) {
        DEFAULT -> openContact.postValue(contact.contact)
        CONTACT_SELECTOR -> updateSelection(contact)
        INTERLOCUTOR_SELECTOR -> when (contact.contact.phoneNumbers.size) {
            0 -> {}
            1 -> addInterlocutor.postValue(contact.contact.contactNumber!!)
            else -> selectInterlocutorNumber.postValue(contact.contact)
        }
    }

    private fun updateSelection(contact: UserContactViewModel) {
        contact.isSelected.apply { set(!get()) }
        hasSelectedItems.set(allContacts.any { it.isSelected.get() })
    }

    fun onSearchClick() {
        adapter.viewType = ContactsAdapter.ViewType.LINEAR
        isSearching.set(true)
    }

    fun onBackClick() = if (isSearching.get()) {
        adapter.viewType = ContactsAdapter.ViewType.GRID
        isSearching.set(false)
        searchQuery.set("")
    } else onBackClickEvents.postValue(Unit)

    fun onApplyClick() =
        selectedContacts.postValue(allContacts.filter { it.isSelected.get() }.map { it.contact })

}