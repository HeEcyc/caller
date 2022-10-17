package com.galala.lalaxy.ui.contacts

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.galala.lalaxy.base.GBaseGViewGModelG
import com.galala.lalaxy.model.contact.UserContact
import com.galala.lalaxy.repository.GContactsGRepositoryG
import com.galala.lalaxy.repository.GLocaleGRepositoryG
import com.galala.lalaxy.repository.GPermissionGRepositoryG

class GContactsGViewGModelG(
    val mode: GContactsGFragmentG.Mode,
    val localeRepository: GLocaleGRepositoryG,
    val contactsRepository: GContactsGRepositoryG,
    val permissionRepository: GPermissionGRepositoryG
) : GBaseGViewGModelG() {

    private val locale = localeRepository.locale ?: GLocaleGRepositoryG.Locale.ENGLISH

    val openContact = MutableLiveData<UserContact>()
    val addInterlocutor = MutableLiveData<String>()
    val selectInterlocutorNumber = MutableLiveData<UserContact>()
    val closeFragment = MutableLiveData<Unit>()
    val hardReloadRV = MutableLiveData<Unit>()

    private val allContacts: List<GContactGAdapterG.ContactViewModel>
    val adapterContacts = GContactGAdapterG(::onContactClick)

    val selectedContacts get() = adapterContacts
        .getData()
        .filter { it is GContactGAdapterG.Contact && it.isSelected.get() }
        .map { (it as GContactGAdapterG.Contact).userContact }

    val isSearching = ObservableBoolean(false)
    val searchQuery = ObservableField("")

    init {
        println("")
        val contacts = getFormattedItems(contactsRepository.contacts)
        println("")
        allContacts = contacts
        println("")
        adapterContacts.reloadData(contacts)
        println("")
        observe(searchQuery) { _, _ ->
            println("")
            adapterContacts.getData().clear()
            println("")
            if (searchQuery.get()?.trim()?.isEmpty() == true)
                allContacts
            else {
                println("")
                allContacts.filter {
                    println("")
                    it is GContactGAdapterG.Contact &&
                            (it.userContact.contactName ?: it.userContact.contactNumber ?: "")
                                .contains(searchQuery.get() ?: "", true)
                }
            }.let(adapterContacts.getData()::addAll)
            println("")
            hardReloadRV.postValue(Unit)
            println("")
        }
    }

    private fun getFormattedItems(contacts: List<UserContact>) =
        if (locale == GLocaleGRepositoryG.Locale.ENGLISH || locale == GLocaleGRepositoryG.Locale.RUSSIAN)
            GContactGAdapterG.getFormattedItemsWithHeaders(contacts)
        else
            GContactGAdapterG.getFormattedItemsWithoutHeaders(contacts)

    private fun onContactClick(contact: GContactGAdapterG.Contact) = when (mode) {
        GContactsGFragmentG.Mode.DEFAULT -> openContact.postValue(contact.userContact)
        GContactsGFragmentG.Mode.CONTACT_SELECTOR -> contact.isSelected.set(!contact.isSelected.get())
        GContactsGFragmentG.Mode.INTERLOCUTOR_SELECTOR -> when (contact.userContact.phoneNumbers.size) {
            0 -> {}
            1 -> addInterlocutor.postValue(contact.userContact.contactNumber!!)
            else -> selectInterlocutorNumber.postValue(contact.userContact)
        }
    }

    fun onBackClick() {
        println("")
        if (isSearching.get()) {
            println("")
            isSearching.set(false)
            println("")
            searchQuery.set("")
            println("")
        } else closeFragment.postValue(Unit)
        println("")
    }

    fun onSearchClick() = isSearching.set(true)

    fun onClearClick() = searchQuery.set("")

}