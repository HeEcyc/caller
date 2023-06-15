package com.yee.zer.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yee.zer.App
import com.yee.zer.base.BaseViewModel
import com.yee.zer.model.contact.UserContact
import com.yee.zer.model.theme.Theme
import com.yee.zer.repository.ContactsRepository
import com.yee.zer.repository.FileRepository
import com.yee.zer.repository.ImagePickerRepository
import com.yee.zer.repository.LocaleRepository
import com.yee.zer.repository.PermissionRepository
import com.yee.zer.repository.PreferencesRepository
import com.yee.zer.repository.ThemeRepository
import com.yee.zer.utils.presetThemes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val themeRepository: ThemeRepository,
    private val imagePickerRepository: ImagePickerRepository,
    val permissionRepository: PermissionRepository,
    private val fileRepository: FileRepository,
    private val contactsRepository: ContactsRepository,
    private val preferencesRepository: PreferencesRepository,
    val localeRepository: LocaleRepository
) : BaseViewModel() {

    val onThemeSelected = MutableLiveData<Theme>()

    val adapterRV = ThemeAdapterHome(::onThemeClick)

    val adapterLanguage = LanguageAdapter(localeRepository) {
        localeRepository.locale = it
    }

    var theme: Theme? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val themes = listOf(
                *themeRepository.getCustomThemes().toTypedArray(),
                *presetThemes.toTypedArray()
            ).mapIndexed { index, theme -> ThemeAdapterHome.Item(theme, index == 0) }
            launch(Dispatchers.Main) {
                adapterRV.reloadData(themes)
            }
            theme = themes.first().theme
            onThemeSelected.postValue(themes.first().theme)
        }
        viewModelScope.launch(Dispatchers.Main) {
            themeRepository.newThemes.collect {
                adapterRV.addItem(ThemeAdapterHome.Item(it), 0)
            }
        }
        adapterLanguage.reloadData(LocaleRepository.Locale.values().toList())
    }

    private fun onThemeClick(theme: Theme) {
        this.theme = theme
        onThemeSelected.postValue(theme)
        adapterRV.getData().forEach { it.isSelected.set(it.theme === theme) }
    }

    fun addNewTheme() {
        imagePickerRepository.pickImage {
            val fileName =
                fileRepository.saveToFileAndGetFilePath(fileRepository.getBitmap(it.uri, App.instance))
            viewModelScope.launch(Dispatchers.IO) { themeRepository.saveNewTheme(fileName) }
        }
    }

    fun applyThemeToContacts(theme: Theme, contacts: List<UserContact>) {
        viewModelScope.launch(Dispatchers.IO) {
            contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        }
    }

    fun applyToAll(theme: Theme) {
        viewModelScope.launch(Dispatchers.IO) {
            contactsRepository.contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        }
    }

    val isFlashOn = ObservableBoolean(preferencesRepository.isFlashOn)
    val isAccelerometerOn = ObservableBoolean(preferencesRepository.isAccelerometerOn)

    fun onFlashClick() {
        preferencesRepository.isFlashOn = !preferencesRepository.isFlashOn
        isFlashOn.set(preferencesRepository.isFlashOn)
    }

    fun onAccelerometerClick() {
        preferencesRepository.isAccelerometerOn = !preferencesRepository.isAccelerometerOn
        isAccelerometerOn.set(preferencesRepository.isAccelerometerOn)
    }

}