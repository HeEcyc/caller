package com.fantasia.telecaller.ui.contact

import androidx.lifecycle.MutableLiveData
import com.fantasia.telecaller.base.BaseViewModel
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.repository.PermissionRepository
import com.fantasia.telecaller.repository.ThemeRepository
import com.fantasia.telecaller.utils.presetThemes
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