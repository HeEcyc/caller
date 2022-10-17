package com.galala.lalaxy.ui.contact

import androidx.lifecycle.MutableLiveData
import com.galala.lalaxy.base.GBaseGViewGModelG
import com.galala.lalaxy.model.contact.UserContact
import com.galala.lalaxy.repository.GPermissionGRepositoryG
import com.galala.lalaxy.repository.GThemeGRepositoryG
import com.galala.lalaxy.utils.presetThemes
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