package com.callerafter.lovelycall.ui.theme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.callerafter.lovelycall.base.BaseViewModel
import com.callerafter.lovelycall.model.contact.UserContact
import com.callerafter.lovelycall.model.theme.Theme
import com.callerafter.lovelycall.repository.call.CallRepository
import com.callerafter.lovelycall.repository.ContactsRepository
import com.callerafter.lovelycall.repository.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThemeViewModel(
    val theme: Theme,
    private val themeRepository: ThemeRepository,
    val callRepository: CallRepository,
    private val contactsRepository: ContactsRepository
) : BaseViewModel() {

    val closeFragment = MutableLiveData<Unit>()
    val onThemeApplied = MutableLiveData<Unit>()

    fun deleteTheme() = viewModelScope.launch(Dispatchers.IO) {
        themeRepository.deleteCustomTheme(theme.backgroundFile)
        closeFragment.postValue(Unit)
    }

    fun applyToAll() = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        onThemeApplied.postValue(Unit)
    }

    fun applyToContacts(contacts: List<UserContact>) = viewModelScope.launch(Dispatchers.IO) {
        contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        onThemeApplied.postValue(Unit)
    }

}