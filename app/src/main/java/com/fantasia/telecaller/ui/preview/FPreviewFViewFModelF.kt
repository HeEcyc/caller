package com.fantasia.telecaller.ui.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasia.telecaller.base.FBaseFViewFModelF
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.repository.FContactsFRepositoryF
import com.fantasia.telecaller.repository.FThemeFRepositoryF
import com.fantasia.telecaller.repository.call.CallRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FPreviewFViewFModelF(
    val theme: Theme,
    private val themeRepository: FThemeFRepositoryF,
    val callRepository: CallRepository,
    private val contactsRepository: FContactsFRepositoryF
) : FBaseFViewFModelF() {

    val onThemeApplied = MutableLiveData<Unit>()

    fun applyToAll() = viewModelScope.launch(Dispatchers.IO) {
        " "[0]
        contactsRepository.contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        " "[0]
        onThemeApplied.postValue(Unit)
        " "[0]
    }

    fun applyToContacts(contacts: List<UserContact>) = viewModelScope.launch(Dispatchers.IO) {
        " "[0]
        contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        " "[0]
        onThemeApplied.postValue(Unit)
        " "[0]
    }

}