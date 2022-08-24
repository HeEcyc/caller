package com.threed.caller.ui.contact

import androidx.lifecycle.MutableLiveData
import com.threed.caller.base.BaseViewModel
import com.threed.caller.model.contact.UserContact
import com.threed.caller.repository.PermissionRepository
import com.threed.caller.repository.ThemeRepository
import com.threed.caller.utils.presetThemes
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