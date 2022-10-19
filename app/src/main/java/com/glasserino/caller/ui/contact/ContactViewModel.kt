package com.glasserino.caller.ui.contact

import androidx.lifecycle.MutableLiveData
import com.glasserino.caller.base.BaseViewModel
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.repository.PermissionRepository
import com.glasserino.caller.repository.ThemeRepository
import com.glasserino.caller.utils.presetThemes
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