package com.pecall.poscreen.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pecall.poscreen.App
import com.pecall.poscreen.base.BaseViewModel
import com.pecall.poscreen.model.contact.UserContact
import com.pecall.poscreen.model.theme.NewTheme
import com.pecall.poscreen.model.theme.Theme
import com.pecall.poscreen.repository.*
import com.pecall.poscreen.utils.presetThemes
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

    val onThemeSelected = MutableLiveData<Int>()

    val adapterVP = ThemeAdapterVP()
    val adapterRV = ThemeAdapterRV(::onThemeClick)

    val adapterLanguage = LanguageAdapter(localeRepository) {
        localeRepository.locale = it
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val themes = listOf(
                NewTheme,
                *themeRepository.getCustomThemes().toTypedArray(),
                *presetThemes.toTypedArray()
            )
            launch(Dispatchers.Main) {
                adapterRV.reloadData(themes.drop(1))
                adapterVP.reloadData(themes)
            }
        }
        viewModelScope.launch(Dispatchers.Main) {
            themeRepository.newThemes.collect {
                adapterRV.addItem(it, 0)
                adapterVP.addItem(it, 1)
            }
        }
        adapterLanguage.reloadData(LocaleRepository.Locale.values().toList())
    }

    private fun onThemeClick(theme: Theme) {
        onThemeSelected.postValue(adapterRV.getData().indexOfFirst { it === theme } + 1)
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