package com.callerafter.lovelycall.ui.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.callerafter.lovelycall.base.BaseViewModel
import com.callerafter.lovelycall.model.contact.UserContact
import com.callerafter.lovelycall.model.theme.Theme
import com.callerafter.lovelycall.repository.PermissionRepository
import com.callerafter.lovelycall.repository.call.CallRepository
import com.callerafter.lovelycall.repository.ThemeRepository
import com.callerafter.lovelycall.utils.defaultTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ContactViewModel(
    val contact: UserContact,
    private val themeRepository: ThemeRepository,
    val callRepository: CallRepository
) : BaseViewModel() {

    lateinit var permissionRepository: PermissionRepository

    val callNumber = MutableLiveData<String>()
    val recreateFragment = MutableLiveData<Unit>()

    val theme = runBlocking(Dispatchers.IO) {
        themeRepository.getContactTheme(contact.contactId) ?: defaultTheme
    }

    val adapter = NumberAdapter(callNumber::postValue)

    init {
        adapter.reloadData(NumberAdapter.getFormattedItemList(contact.phoneNumbers))
    }

    fun applyTheme(theme: Theme) = viewModelScope.launch(Dispatchers.IO) {
        themeRepository.setContactTheme(contact.contactId, theme.backgroundFile)
        recreateFragment.postValue(Unit)
    }

}