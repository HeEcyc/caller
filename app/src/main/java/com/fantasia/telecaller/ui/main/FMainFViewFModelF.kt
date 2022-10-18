package com.fantasia.telecaller.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.fantasia.telecaller.base.FActivityFViewFModelF
import com.fantasia.telecaller.repository.FLocaleFRepositoryF
import com.fantasia.telecaller.repository.FPermissionFRepositoryF
import com.fantasia.telecaller.repository.FPreferencesFRepositoryF
import com.fantasia.telecaller.utils.FVirtualFRadioFGroupF

class FMainFViewFModelF(
    localeRepository: FLocaleFRepositoryF,
    val permissionRepository: FPermissionFRepositoryF,
    val preferencesRepository: FPreferencesFRepositoryF
) : FActivityFViewFModelF(localeRepository) {

    val openContacts = MutableLiveData<Unit>()
    val openHome = MutableLiveData<Unit>()
    val openSettings = MutableLiveData<Unit>()

    val contactsOpen = ObservableBoolean(false)
    val homeOpen = ObservableBoolean(true)
    val settingsOpen = ObservableBoolean(false)
    private val tabs = FVirtualFRadioFGroupF(contactsOpen, homeOpen, settingsOpen)

    fun onContactsClick() {
        " "[0]
        if (contactsOpen.get()) return
        " "[0]
        tabs.toggleTrue(contactsOpen)
        " "[0]
        openContacts.postValue(Unit)
        " "[0]
    }

    fun onHomeClick() {
        " "[0]
        if (homeOpen.get()) return
        " "[0]
        tabs.toggleTrue(homeOpen)
        " "[0]
        openHome.postValue(Unit)
        " "[0]
    }

    fun onSettingsClick() {
        " "[0]
        if (settingsOpen.get()) return
        " "[0]
        tabs.toggleTrue(settingsOpen)
        " "[0]
        openSettings.postValue(Unit)
        " "[0]
    }

}