package com.glass.call.ui.contact

import androidx.lifecycle.MutableLiveData
import com.glass.call.base.BaseViewModel
import com.glass.call.model.contact.UserContact
import com.glass.call.repository.PermissionRepository
import com.glass.call.repository.ThemeRepository
import com.glass.call.utils.presetThemes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class ContactViewModel(
    val contact: UserContact,
    val permissionRepository: PermissionRepository,
    private val themeRepository: ThemeRepository
) : BaseViewModel() {

    val callNumber = MutableLiveData<String>()

    val theme = runBlocking(Dispatchers.IO) {
        themeRepository.getContactTheme(contact.contactId) ?: presetThemes.first()
    }
    val adapter = NumberAdapter(callNumber::postValue)

    init {
        adapter.reloadData(contact.phoneNumbers)
    }

}