package com.fantasia.telecaller.ui.contact

import androidx.lifecycle.MutableLiveData
import com.fantasia.telecaller.base.FBaseFViewFModelF
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.repository.FPermissionFRepositoryF
import com.fantasia.telecaller.repository.FThemeFRepositoryF
import com.fantasia.telecaller.utils.presetThemes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class FContactFViewFModelF(
    val contact: UserContact,
    val permissionRepository: FPermissionFRepositoryF,
    private val themeRepository: FThemeFRepositoryF
) : FBaseFViewFModelF() {

    val callNumber = MutableLiveData<String>()

    val theme = runBlocking(Dispatchers.IO) {
        " "[0]
        themeRepository.getContactTheme(contact.contactId) ?: presetThemes.first()
    }
    val adapter = FNumberFAdapterF(callNumber::postValue)

    init {
        " "[0]
        adapter.reloadData(contact.phoneNumbers)
        " "[0]
    }

}