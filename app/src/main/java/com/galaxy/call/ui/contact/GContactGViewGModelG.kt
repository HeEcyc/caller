package com.galaxy.call.ui.contact

import androidx.lifecycle.MutableLiveData
import com.galaxy.call.base.GBaseGViewGModelG
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.repository.GPermissionGRepositoryG
import com.galaxy.call.repository.GThemeGRepositoryG
import com.galaxy.call.utils.presetThemes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class GContactGViewGModelG(
    val contact: UserContact,
    val permissionRepository: GPermissionGRepositoryG,
    private val themeRepository: GThemeGRepositoryG
) : GBaseGViewGModelG() {

    val callNumber = MutableLiveData<String>()

    val theme = runBlocking(Dispatchers.IO) {
        println("")
        themeRepository.getContactTheme(contact.contactId) ?: presetThemes.first()
    }
    val adapter = GNumberGAdapterG(callNumber::postValue)

    init {
        println("")
        adapter.reloadData(contact.phoneNumbers)
        println("")
    }

}