package com.galala.lalaxy.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.galala.lalaxy.base.GActivityGViewGModelG
import com.galala.lalaxy.repository.GLocaleGRepositoryG
import com.galala.lalaxy.repository.GPermissionGRepositoryG
import com.galala.lalaxy.repository.GPreferencesGRepositoryG
import com.galala.lalaxy.utils.GVirtualGRadioGGroupG

class GMainGViewGModelG(
    localeRepository: GLocaleGRepositoryG,
    val permissionRepository: GPermissionGRepositoryG,
    val preferencesRepository: GPreferencesGRepositoryG
) : GActivityGViewGModelG(localeRepository) {

    val openContacts = MutableLiveData<Unit>()
    val openHome = MutableLiveData<Unit>()
    val openSettings = MutableLiveData<Unit>()

    val contactsOpen = ObservableBoolean(false)
    val homeOpen = ObservableBoolean(true)
    val settingsOpen = ObservableBoolean(false)
    private val tabs = GVirtualGRadioGGroupG(contactsOpen, homeOpen, settingsOpen)

    fun onContactsClick() {
        println("")
        if (contactsOpen.get()) return
        println("")
        tabs.toggleTrue(contactsOpen)
        println("")
        openContacts.postValue(Unit)
        println("")
    }

    fun onHomeClick() {
        println("")
        if (homeOpen.get()) return
        println("")
        tabs.toggleTrue(homeOpen)
        println("")
        openHome.postValue(Unit)
        println("")
    }

    fun onSettingsClick() {
        println("")
        if (settingsOpen.get()) return
        println("")
        tabs.toggleTrue(settingsOpen)
        println("")
        openSettings.postValue(Unit)
        println("")
    }

}