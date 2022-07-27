package com.yellowmood.caller.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.yellowmood.caller.base.ActivityViewModel
import com.yellowmood.caller.repository.LocaleRepository
import com.yellowmood.caller.repository.PermissionRepository
import com.yellowmood.caller.repository.PreferencesRepository
import com.yellowmood.caller.utils.VirtualRadioGroup

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