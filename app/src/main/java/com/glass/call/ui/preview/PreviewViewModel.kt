package com.glass.call.ui.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glass.call.base.BaseViewModel
import com.glass.call.model.contact.UserContact
import com.glass.call.model.theme.Theme
import com.glass.call.repository.ContactsRepository
import com.glass.call.repository.ThemeRepository
import com.glass.call.repository.call.CallRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreviewViewModel(
    val theme: Theme,
    private val themeRepository: ThemeRepository,
    val callRepository: CallRepository,
    private val contactsRepository: ContactsRepository
) : BaseViewModel() {

    val onThemeApplied = MutableLiveData<Unit>()

    fun applyToAll() = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        onThemeApplied.postValue(Unit)
    }

    fun applyToContacts(contacts: List<UserContact>) = viewModelScope.launch(Dispatchers.IO) {
        contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        onThemeApplied.postValue(Unit)
    }

}