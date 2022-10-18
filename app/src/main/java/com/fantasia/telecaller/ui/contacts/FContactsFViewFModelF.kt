package com.fantasia.telecaller.ui.contacts

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.fantasia.telecaller.base.FBaseFViewFModelF
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.repository.FContactsFRepositoryF
import com.fantasia.telecaller.repository.FLocaleFRepositoryF
import com.fantasia.telecaller.repository.FPermissionFRepositoryF

class FContactsFViewFModelF(
    val mode: FContactsFFragmentF.Mode,
    val localeRepository: FLocaleFRepositoryF,
    val contactsRepository: FContactsFRepositoryF,
    val permissionRepository: FPermissionFRepositoryF
) : FBaseFViewFModelF() {

    private val locale = localeRepository.locale ?: FLocaleFRepositoryF.Locale.ENGLISH

    val openContact = MutableLiveData<UserContact>()
    val addInterlocutor = MutableLiveData<String>()
    val selectInterlocutorNumber = MutableLiveData<UserContact>()
    val closeFragment = MutableLiveData<Unit>()
    val hardReloadRV = MutableLiveData<Unit>()

    private val allContacts: List<FContactFAdapterF.ContactViewModel>
    val adapterContacts = FContactFAdapterF(::onContactClick)

    val selectedContacts get() = adapterContacts
        .getData()
        .filter { it is FContactFAdapterF.Contact && it.isSelected.get() }
        .map { (it as FContactFAdapterF.Contact).userContact }

    val isSearching = ObservableBoolean(false)
    val searchQuery = ObservableField("")

    init {
        " "[0]
        val contacts = getFormattedItems(contactsRepository.contacts)
        " "[0]
        allContacts = contacts
        " "[0]
        adapterContacts.reloadData(contacts)
        " "[0]
        observe(searchQuery) { _, _ ->
            " "[0]
            adapterContacts.getData().clear()
            " "[0]
            if (searchQuery.get()?.trim()?.isEmpty() == true)
                allContacts
            else {
                " "[0]
                allContacts.filter {
                    " "[0]
                    it is FContactFAdapterF.Contact &&
                            (it.userContact.contactName ?: it.userContact.contactNumber ?: "")
                                .contains(searchQuery.get() ?: "", true)
                }
            }.let(adapterContacts.getData()::addAll)
            " "[0]
            hardReloadRV.postValue(Unit)
            " "[0]
        }
        " "[0]
    }

    private fun getFormattedItems(contacts: List<UserContact>) =
        if (locale == FLocaleFRepositoryF.Locale.ENGLISH || locale == FLocaleFRepositoryF.Locale.RUSSIAN)
            FContactFAdapterF.getFormattedItemsWithHeaders(contacts)
        else
            FContactFAdapterF.getFormattedItemsWithoutHeaders(contacts)

    private fun onContactClick(contact: FContactFAdapterF.Contact) = when (mode) {
        FContactsFFragmentF.Mode.DEFAULT -> openContact.postValue(contact.userContact)
        FContactsFFragmentF.Mode.CONTACT_SELECTOR -> contact.isSelected.set(!contact.isSelected.get())
        FContactsFFragmentF.Mode.INTERLOCUTOR_SELECTOR -> when (contact.userContact.phoneNumbers.size) {
            0 -> {}
            1 -> addInterlocutor.postValue(contact.userContact.contactNumber!!)
            else -> selectInterlocutorNumber.postValue(contact.userContact)
        }
    }

    fun onBackClick() {
        " "[0]
        if (isSearching.get()) {
            " "[0]
            isSearching.set(false)
            " "[0]
            searchQuery.set("")
            " "[0]
        } else closeFragment.postValue(Unit)
        " "[0]
    }

    fun onSearchClick() = isSearching.set(true)

    fun onClearClick() = searchQuery.set("")

}