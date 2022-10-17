package com.galaxy.call.ui.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.galaxy.call.base.GBaseGViewGModelG
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.model.theme.Theme
import com.galaxy.call.repository.GContactsGRepositoryG
import com.galaxy.call.repository.GThemeGRepositoryG
import com.galaxy.call.repository.call.GCallGRepositoryG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GPreviewGViewGModelG(
    val theme: Theme,
    private val themeRepository: GThemeGRepositoryG,
    val callRepository: GCallGRepositoryG,
    private val contactsRepository: GContactsGRepositoryG
) : GBaseGViewGModelG() {

    val onThemeApplied = MutableLiveData<Unit>()

    fun applyToAll() = viewModelScope.launch(Dispatchers.IO) {
        println("")
        contactsRepository.contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        println("")
        onThemeApplied.postValue(Unit)
        println("")
    }

    fun applyToContacts(contacts: List<UserContact>) = viewModelScope.launch(Dispatchers.IO) {
        println("")
        contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        println("")
        onThemeApplied.postValue(Unit)
        println("")
    }

}