package com.maxios.maxcall.ui.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maxios.maxcall.base.BaseViewModel
import com.maxios.maxcall.model.contact.UserContact
import com.maxios.maxcall.model.theme.Theme
import com.maxios.maxcall.repository.PermissionRepository
import com.maxios.maxcall.repository.ThemeRepository
import com.maxios.maxcall.utils.defaultTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ContactViewModel(
    val contact: UserContact,
    val permissionRepository: PermissionRepository,
    private val themeRepository: ThemeRepository
) : BaseViewModel() {

    val callNumber = MutableLiveData<String>()
    val recreateFragment = MutableLiveData<Unit>()

    val theme = runBlocking(Dispatchers.IO) {
        themeRepository.getContactTheme(contact.contactId) ?: defaultTheme
    }
    val adapter = NumberAdapter(callNumber::postValue)

    init {
        adapter.reloadData(contact.phoneNumbers)
    }

    fun setContactTheme(theme: Theme) = viewModelScope.launch(Dispatchers.IO) {
        themeRepository.setContactTheme(contact.contactId, theme.backgroundFile)
        recreateFragment.postValue(Unit)
    }

}