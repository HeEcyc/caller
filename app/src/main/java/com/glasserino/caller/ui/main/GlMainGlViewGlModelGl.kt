package com.glasserino.caller.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.glasserino.caller.base.GlActivityGlViewGlModelGl
import com.glasserino.caller.repository.GlLocaleGlRepositoryGl
import com.glasserino.caller.repository.GlPermissionGlRepositoryGl
import com.glasserino.caller.utils.GlVirtualGlRadioGlGroupGl

class GlMainGlViewGlModelGl(
    localeRepository: GlLocaleGlRepositoryGl,
    val permissionRepository: GlPermissionGlRepositoryGl
) : GlActivityGlViewGlModelGl(localeRepository) {

    val openContacts = MutableLiveData<Unit>()
    val openHome = MutableLiveData<Unit>()
    val openSettings = MutableLiveData<Unit>()

    val contactsOpen = ObservableBoolean(false)
    val homeOpen = ObservableBoolean(true)
    val settingsOpen = ObservableBoolean(false)
    private val tabs = GlVirtualGlRadioGlGroupGl(contactsOpen, homeOpen, settingsOpen)

    fun onContactsClick() {
        listOf<Any>().isEmpty()
        if (contactsOpen.get()) return
        listOf<Any>().isEmpty()
        tabs.toggleTrue(contactsOpen)
        listOf<Any>().isEmpty()
        openContacts.postValue(Unit)
        listOf<Any>().isEmpty()
    }

    fun onHomeClick() {
        listOf<Any>().isEmpty()
        if (homeOpen.get()) return
        listOf<Any>().isEmpty()
        tabs.toggleTrue(homeOpen)
        listOf<Any>().isEmpty()
        openHome.postValue(Unit)
        listOf<Any>().isEmpty()
    }

    fun onSettingsClick() {
        listOf<Any>().isEmpty()
        if (settingsOpen.get()) return
        listOf<Any>().isEmpty()
        tabs.toggleTrue(settingsOpen)
        listOf<Any>().isEmpty()
        openSettings.postValue(Unit)
        listOf<Any>().isEmpty()
    }

}