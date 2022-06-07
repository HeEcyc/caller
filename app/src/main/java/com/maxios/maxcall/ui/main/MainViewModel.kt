package com.maxios.maxcall.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.maxios.maxcall.base.ActivityViewModel
import com.maxios.maxcall.repository.LocaleRepository
import com.maxios.maxcall.repository.PermissionRepository
import com.maxios.maxcall.repository.PreferencesRepository

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

    fun onContactsClick() {
        if (contactsOpen.get()) return
        contactsOpen.set(true)
        homeOpen.set(false)
        settingsOpen.set(false)
        openContacts.postValue(Unit)
    }

    fun onHomeClick() {
        if (homeOpen.get()) return
        contactsOpen.set(false)
        homeOpen.set(true)
        settingsOpen.set(false)
        openHome.postValue(Unit)
    }

    fun onSettingsClick() {
        if (settingsOpen.get()) return
        contactsOpen.set(false)
        homeOpen.set(false)
        settingsOpen.set(true)
        openSettings.postValue(Unit)
    }

}