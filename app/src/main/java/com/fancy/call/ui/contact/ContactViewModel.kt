package com.fancy.call.ui.contact

import androidx.lifecycle.MutableLiveData
import com.fancy.call.base.BaseViewModel
import com.fancy.call.model.contact.UserContact
import com.fancy.call.repository.PermissionRepository
import com.fancy.call.repository.ThemeRepository
import com.fancy.call.utils.presetThemes
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