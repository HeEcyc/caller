package com.glass.call.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glass.call.App
import com.glass.call.base.BaseViewModel
import com.glass.call.model.contact.UserContact
import com.glass.call.model.theme.Theme
import com.glass.call.repository.FileRepository
import com.glass.call.repository.ImagePickerRepository
import com.glass.call.repository.PermissionRepository
import com.glass.call.repository.ThemeRepository
import com.glass.call.utils.VirtualRadioGroup
import com.glass.call.utils.presetThemes
import com.glass.call.utils.themesNew
import com.glass.call.utils.themesTop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val themeRepository: ThemeRepository,
    private val imagePickerRepository: ImagePickerRepository,
    val permissionRepository: PermissionRepository,
    private val fileRepository: FileRepository
) : BaseViewModel() {

    val onThemeSelected = MutableLiveData<Theme>()

    val showTabPreset = ObservableBoolean(true)
    val showTabCustom = ObservableBoolean(false)
    private val tabsMain = VirtualRadioGroup(showTabPreset, showTabCustom)

    val showTabNew = ObservableBoolean(true)
    val showTabTop = ObservableBoolean(false)
    val showTabAll = ObservableBoolean(false)
    private val tabsPreset = VirtualRadioGroup(showTabNew, showTabTop, showTabAll)

    val hasAllPermissions = ObservableBoolean(permissionRepository.hasNecessaryPermissions)

    val adapterCustom = ThemeAdapter(onThemeSelected::postValue)
    val adapterPreset1 = ThemeAdapter(onThemeSelected::postValue)
    val adapterPreset2 = ThemeAdapter(onThemeSelected::postValue)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val themes: List<Theme> = themeRepository.getCustomThemes()
            launch(Dispatchers.Main) {
                adapterCustom.reloadData(themes.mapIndexed { index, theme -> ThemeAdapter.ThemeViewModel(theme, index == 0) })
            }
        }
        reloadPresetAdapterData(themesNew)
        viewModelScope.launch(Dispatchers.Main) {
            themeRepository.newThemes.collect {
                adapterCustom.getData().firstOrNull()?.isDemo?.set(false)
                adapterCustom.addItem(ThemeAdapter.ThemeViewModel(it, true), 0)
            }
        }
    }

    fun onTabPresetClick() = tabsMain.toggleTrue(showTabPreset)

    fun onTabCustomClick() = tabsMain.toggleTrue(showTabCustom)

    fun onTabNewClick() {
        if (showTabNew.get()) return
        tabsPreset.toggleTrue(showTabNew)
        reloadPresetAdapterData(themesNew)
    }

    fun onTabTopClick() {
        if (showTabTop.get()) return
        tabsPreset.toggleTrue(showTabTop)
        reloadPresetAdapterData(themesTop)
    }

    fun onTabAllClick() {
        if (showTabAll.get()) return
        tabsPreset.toggleTrue(showTabAll)
        reloadPresetAdapterData(presetThemes)
    }

    private fun reloadPresetAdapterData(newData: List<Theme>) {
        val data1 = newData
            .filterIndexed { index, _ -> index % 2 == 0 }
            .mapIndexed { index, theme -> ThemeAdapter.ThemeViewModel(theme, index == 0) }
        val data2 = newData
            .filterIndexed { index, _ -> index % 2 == 1 }
            .map { theme -> ThemeAdapter.ThemeViewModel(theme, false) }
        adapterPreset1.reloadData(data1)
        adapterPreset2.reloadData(data2)
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