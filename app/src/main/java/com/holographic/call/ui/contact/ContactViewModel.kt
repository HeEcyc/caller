package com.holographic.call.ui.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.holographic.call.base.BaseViewModel
import com.holographic.call.model.contact.UserContact
import com.holographic.call.model.theme.Theme
import com.holographic.call.repository.PermissionRepository
import com.holographic.call.repository.ThemeRepository
import com.holographic.call.utils.defaultTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ContactViewModel(
    val contact: UserContact,
    val permissionRepository: PermissionRepository,
    private val themeRepository: ThemeRepository
) : BaseViewModel() {

    val callNumber = MutableLiveData<String>()
    val recreateFragment = MutableLiveData<Unit>()

    val theme = runBlocking(Dispatchers.IO) {
        themeRepository.getContactTheme(contact.contactId) ?: defaultTheme
    }
    val adapter = NumberAdapter(callNumber::postValue)

    init {
        adapter.reloadData(contact.phoneNumbers)
    }

    fun setContactTheme(theme: Theme) = viewModelScope.launch(Dispatchers.IO) {
        themeRepository.setContactTheme(contact.contactId, theme.backgroundFile)
        recreateFragment.postValue(Unit)
    }

}