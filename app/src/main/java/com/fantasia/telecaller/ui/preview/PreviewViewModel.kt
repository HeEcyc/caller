package com.fantasia.telecaller.ui.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasia.telecaller.base.BaseViewModel
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.repository.ContactsRepository
import com.fantasia.telecaller.repository.ThemeRepository
import com.fantasia.telecaller.repository.call.CallRepository
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