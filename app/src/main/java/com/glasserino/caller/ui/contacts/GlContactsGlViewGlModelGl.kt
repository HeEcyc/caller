package com.glasserino.caller.ui.contacts

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.glasserino.caller.base.GlBaseGlViewGlModelGl
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.repository.GlContactsGlRepositoryGl
import com.glasserino.caller.repository.GlLocaleGlRepositoryGl
import com.glasserino.caller.repository.GlPermissionGlRepositoryGl

class GlContactsGlViewGlModelGl(
    val mode: GlContactsGlFragmentGl.Mode,
    val localeRepository: GlLocaleGlRepositoryGl,
    val contactsRepository: GlContactsGlRepositoryGl,
    val permissionRepository: GlPermissionGlRepositoryGl
) : GlBaseGlViewGlModelGl() {

    private val locale = localeRepository.locale ?: GlLocaleGlRepositoryGl.Locale.ENGLISH

    val openContact = MutableLiveData<UserContact>()
    val addInterlocutor = MutableLiveData<String>()
    val selectInterlocutorNumber = MutableLiveData<UserContact>()
    val closeFragment = MutableLiveData<Unit>()
    val hardReloadRV = MutableLiveData<Unit>()

    private val allContacts: List<GlContactGlAdapterGL.ContactViewModel>
    val adapterContacts = GlContactGlAdapterGL(::onContactClick)

    val selectedContacts get() = adapterContacts
        .getData()
        .filter { it is GlContactGlAdapterGL.Contact && it.isSelected.get() }
        .map { (it as GlContactGlAdapterGL.Contact).userContact }

    val isSearching = ObservableBoolean(false)
    val searchQuery = ObservableField("")

    init {
        listOf<Any>().isEmpty()
        val contacts = getFormattedItems(contactsRepository.contacts)
        listOf<Any>().isEmpty()
        allContacts = contacts
        listOf<Any>().isEmpty()
        adapterContacts.reloadData(contacts)
        listOf<Any>().isEmpty()
        observe(searchQuery) { _, _ ->
            listOf<Any>().isEmpty()
            adapterContacts.getData().clear()
            listOf<Any>().isEmpty()
            if (searchQuery.get()?.trim()?.isEmpty() == true)
                allContacts
            else {
                listOf<Any>().isEmpty()
                allContacts.filter {
                    listOf<Any>().isEmpty()
                    it is GlContactGlAdapterGL.Contact &&
                            (it.userContact.contactName ?: it.userContact.contactNumber ?: "")
                                .contains(searchQuery.get() ?: "", true)
                }
            }.let(adapterContacts.getData()::addAll)
            listOf<Any>().isEmpty()
            hardReloadRV.postValue(Unit)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    private fun getFormattedItems(contacts: List<UserContact>) =
        if (locale == GlLocaleGlRepositoryGl.Locale.ENGLISH || locale == GlLocaleGlRepositoryGl.Locale.RUSSIAN)
            GlContactGlAdapterGL.getFormattedItemsWithHeaders(contacts)
        else
            GlContactGlAdapterGL.getFormattedItemsWithoutHeaders(contacts)

    private fun onContactClick(contact: GlContactGlAdapterGL.Contact) = when (mode) {
        GlContactsGlFragmentGl.Mode.DEFAULT -> openContact.postValue(contact.userContact)
        GlContactsGlFragmentGl.Mode.CONTACT_SELECTOR -> contact.isSelected.set(!contact.isSelected.get())
        GlContactsGlFragmentGl.Mode.INTERLOCUTOR_SELECTOR -> when (contact.userContact.phoneNumbers.size) {
            0 -> {}
            1 -> addInterlocutor.postValue(contact.userContact.contactNumber!!)
            else -> selectInterlocutorNumber.postValue(contact.userContact)
        }
    }

    fun onBackClick() {
        listOf<Any>().isEmpty()
        if (isSearching.get()) {
            listOf<Any>().isEmpty()
            isSearching.set(false)
            listOf<Any>().isEmpty()
            searchQuery.set("")
            listOf<Any>().isEmpty()
        } else closeFragment.postValue(Unit)
        listOf<Any>().isEmpty()
    }

    fun onSearchClick() = isSearching.set(true)

    fun onClearClick() = searchQuery.set("")

}