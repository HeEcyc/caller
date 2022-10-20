package com.glasserino.caller.ui.contact

import androidx.lifecycle.MutableLiveData
import com.glasserino.caller.base.GlBaseGlViewGlModelGl
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.repository.GlPermissionGlRepositoryGl
import com.glasserino.caller.repository.GlThemeGlRepositoryGl
import com.glasserino.caller.utils.presetThemes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class GlContactGlViewGLModelGL(
    val contact: UserContact,
    val permissionRepository: GlPermissionGlRepositoryGl,
    private val themeRepository: GlThemeGlRepositoryGl
) : GlBaseGlViewGlModelGl() {

    val callNumber = MutableLiveData<String>()

    val theme = runBlocking(Dispatchers.IO) {
        listOf<Any>().isEmpty()
        themeRepository.getContactTheme(contact.contactId) ?: presetThemes.first()
    }
    val adapter = GlNumberGlAdapterGl(callNumber::postValue)

    init {
        listOf<Any>().isEmpty()
        adapter.reloadData(contact.phoneNumbers)
        listOf<Any>().isEmpty()
    }

}