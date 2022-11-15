package com.roobcall.themes.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.roobcall.themes.base.ActivityViewModel
import com.roobcall.themes.repository.LocaleRepository
import com.roobcall.themes.repository.PermissionRepository
import com.roobcall.themes.repository.PreferencesRepository
import com.roobcall.themes.utils.VirtualRadioGroup

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository) {

    val openContacts = MutableLiveData<Unit>()
    val openHome = MutableLiveData<Unit>()
    val openSettings = MutableLiveData<Unit>()

    val contactsOpen = ObservableBoolean(false)
    val homeOpen = ObservableBoolean(true)
    val settingsOpen = ObservableBoolean(false)
    private val tabs = VirtualRadioGroup(contactsOpen, homeOpen, settingsOpen)

    fun onContactsClick() {
        if (contactsOpen.get()) return
        tabs.toggleTrue(contactsOpen)
        openContacts.postValue(Unit)
    }

    fun onHomeClick() {
        if (homeOpen.get()) return
        tabs.toggleTrue(homeOpen)
        openHome.postValue(Unit)
    }

    fun onSettingsClick() {
        if (settingsOpen.get()) return
        tabs.toggleTrue(settingsOpen)
        openSettings.postValue(Unit)
    }

}