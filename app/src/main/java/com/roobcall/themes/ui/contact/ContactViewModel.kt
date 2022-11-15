package com.roobcall.themes.ui.contact

import androidx.lifecycle.MutableLiveData
import com.roobcall.themes.base.BaseViewModel
import com.roobcall.themes.model.contact.UserContact
import com.roobcall.themes.repository.PermissionRepository
import com.roobcall.themes.repository.ThemeRepository
import com.roobcall.themes.utils.presetThemes
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