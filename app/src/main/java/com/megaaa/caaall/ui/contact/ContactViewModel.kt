package com.megaaa.caaall.ui.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.megaaa.caaall.base.BaseViewModel
import com.megaaa.caaall.model.contact.UserContact
import com.megaaa.caaall.model.theme.Theme
import com.megaaa.caaall.repository.PermissionRepository
import com.megaaa.caaall.repository.call.CallRepository
import com.megaaa.caaall.repository.ThemeRepository
import com.megaaa.caaall.utils.defaultTheme
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