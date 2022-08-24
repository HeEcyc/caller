package com.threed.caller.ui.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.threed.caller.base.BaseViewModel
import com.threed.caller.model.contact.UserContact
import com.threed.caller.model.theme.Theme
import com.threed.caller.repository.ContactsRepository
import com.threed.caller.repository.ThemeRepository
import com.threed.caller.repository.call.CallRepository
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