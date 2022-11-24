package com.vefercal.ler.ui.contact

import androidx.lifecycle.MutableLiveData
import com.vefercal.ler.base.BaseViewModel
import com.vefercal.ler.model.contact.UserContact
import com.vefercal.ler.repository.PermissionRepository
import com.vefercal.ler.repository.ThemeRepository
import com.vefercal.ler.utils.presetThemes
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