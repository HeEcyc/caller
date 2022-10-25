package com.stacky.caller.ui.contact

import androidx.lifecycle.MutableLiveData
import com.stacky.caller.base.BaseViewModel
import com.stacky.caller.model.contact.UserContact
import com.stacky.caller.repository.PermissionRepository
import com.stacky.caller.repository.ThemeRepository
import com.stacky.caller.utils.presetThemes
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