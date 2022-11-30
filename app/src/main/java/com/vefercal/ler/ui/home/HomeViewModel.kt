package com.vefercal.ler.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vefercal.ler.App
import com.vefercal.ler.base.BaseViewModel
import com.vefercal.ler.model.contact.UserContact
import com.vefercal.ler.model.theme.NewTheme
import com.vefercal.ler.model.theme.Theme
import com.vefercal.ler.repository.FileRepository
import com.vefercal.ler.repository.ImagePickerRepository
import com.vefercal.ler.repository.PermissionRepository
import com.vefercal.ler.repository.ThemeRepository
import com.vefercal.ler.utils.presetThemes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val themeRepository: ThemeRepository,
    private val imagePickerRepository: ImagePickerRepository,
    val permissionRepository: PermissionRepository,
    private val fileRepository: FileRepository
) : BaseViewModel() {

    val onThemeSelected = MutableLiveData<Int>()

    val adapterVP = ThemeAdapterVP()
    val adapterRV = ThemeAdapterRV(::onThemeClick)

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

}