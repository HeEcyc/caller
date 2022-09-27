package com.sappy.callthemes.ui.contact

import androidx.lifecycle.MutableLiveData
import com.sappy.callthemes.base.BaseViewModel
import com.sappy.callthemes.model.contact.UserContact
import com.sappy.callthemes.repository.PermissionRepository
import com.sappy.callthemes.repository.ThemeRepository
import com.sappy.callthemes.utils.presetThemes
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