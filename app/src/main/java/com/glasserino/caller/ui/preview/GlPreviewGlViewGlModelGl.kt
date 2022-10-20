package com.glasserino.caller.ui.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glasserino.caller.base.GlBaseGlViewGlModelGl
import com.glasserino.caller.model.theme.Theme
import com.glasserino.caller.repository.GlContactsGlRepositoryGl
import com.glasserino.caller.repository.GlThemeGlRepositoryGl
import com.glasserino.caller.repository.call.CallRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GlPreviewGlViewGlModelGl(
    val theme: Theme,
    private val themeRepository: GlThemeGlRepositoryGl,
    val callRepository: CallRepository,
    private val contactsRepository: GlContactsGlRepositoryGl
) : GlBaseGlViewGlModelGl() {

    val onThemeApplied = MutableLiveData<Unit>()

    fun applyToAll() = viewModelScope.launch(Dispatchers.IO) {
        listOf<Any>().isEmpty()
        contactsRepository.contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        listOf<Any>().isEmpty()
        onThemeApplied.postValue(Unit)
        listOf<Any>().isEmpty()
    }

}