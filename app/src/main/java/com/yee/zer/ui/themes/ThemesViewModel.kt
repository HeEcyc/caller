package com.yee.zer.ui.themes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yee.zer.base.ActivityViewModel
import com.yee.zer.model.contact.UserContact
import com.yee.zer.model.theme.Theme
import com.yee.zer.repository.LocaleRepository
import com.yee.zer.repository.PermissionRepository
import com.yee.zer.repository.ThemeRepository
import com.yee.zer.ui.home.ThemeAdapter
import com.yee.zer.utils.presetThemes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThemesViewModel(
    private val themeRepository: ThemeRepository,
    val permissionRepository: PermissionRepository,
    localeRepository: LocaleRepository
) : ActivityViewModel(localeRepository) {

    val onThemeSelected = MutableLiveData<Theme>()

    val adapter = ThemeAdapter(::onThemeClick)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val themes = listOf(
                *themeRepository.getCustomThemes().toTypedArray(),
                *presetThemes.toTypedArray()
            )
            launch(Dispatchers.Main) {
                adapter.reloadData(themes)
            }
        }
    }

    private fun onThemeClick(theme: Theme) {
        onThemeSelected.postValue(theme)
    }

    fun applyThemeToContacts(theme: Theme, contacts: List<UserContact>) {
        viewModelScope.launch(Dispatchers.IO) {
            contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        }
    }

}