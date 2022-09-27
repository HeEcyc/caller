package com.sappy.callthemes.ui.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sappy.callthemes.base.BaseViewModel
import com.sappy.callthemes.model.contact.UserContact
import com.sappy.callthemes.model.theme.Theme
import com.sappy.callthemes.repository.ContactsRepository
import com.sappy.callthemes.repository.ThemeRepository
import com.sappy.callthemes.repository.call.CallRepository
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