package com.galala.lalaxy.ui.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.galala.lalaxy.base.GBaseGViewGModelG
import com.galala.lalaxy.model.contact.UserContact
import com.galala.lalaxy.model.theme.Theme
import com.galala.lalaxy.repository.GContactsGRepositoryG
import com.galala.lalaxy.repository.GThemeGRepositoryG
import com.galala.lalaxy.repository.call.GCallGRepositoryG
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