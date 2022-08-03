package com.fantasy.call.ui.contact

import androidx.lifecycle.MutableLiveData
import com.fantasy.call.base.BaseViewModel
import com.fantasy.call.model.contact.UserContact
import com.fantasy.call.repository.PermissionRepository
import com.fantasy.call.repository.ThemeRepository
import com.fantasy.call.utils.presetThemes
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