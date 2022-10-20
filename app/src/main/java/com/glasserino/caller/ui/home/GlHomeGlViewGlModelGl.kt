package com.glasserino.caller.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glasserino.caller.GlAppGl
import com.glasserino.caller.base.GlBaseGlViewGlModelGl
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.model.theme.Theme
import com.glasserino.caller.repository.GlFileGlRepositoryGL
import com.glasserino.caller.repository.GlImageGlPickerGlRepositoryGl
import com.glasserino.caller.repository.GlPermissionGlRepositoryGl
import com.glasserino.caller.repository.GlThemeGlRepositoryGl
import com.glasserino.caller.utils.GlVirtualGlRadioGlGroupGl
import com.glasserino.caller.utils.presetThemes
import com.glasserino.caller.utils.themesNew
import com.glasserino.caller.utils.themesTop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GlHomeGlViewGlModelGl(
    private val themeRepository: GlThemeGlRepositoryGl,
    private val imagePickerRepository: GlImageGlPickerGlRepositoryGl,
    val permissionRepository: GlPermissionGlRepositoryGl,
    private val fileRepository: GlFileGlRepositoryGL
) : GlBaseGlViewGlModelGl() {

    val onThemeSelected = MutableLiveData<Theme>()

    val showTabPreset = ObservableBoolean(true)
    val showTabCustom = ObservableBoolean(false)
    private val tabsMain = GlVirtualGlRadioGlGroupGl(showTabPreset, showTabCustom)

    val showTabNew = ObservableBoolean(true)
    val showTabTop = ObservableBoolean(false)
    val showTabAll = ObservableBoolean(false)
    private val tabsPreset = GlVirtualGlRadioGlGroupGl(showTabNew, showTabTop, showTabAll)

    val hasAllPermissions = ObservableBoolean(permissionRepository.hasNecessaryPermissions)

    val adapterCustom = GlThemeGlAdapterGl(onThemeSelected::postValue)
    val adapterPreset1 = GlThemeGlAdapterGl(onThemeSelected::postValue)
    val adapterPreset2 = GlThemeGlAdapterGl(onThemeSelected::postValue)

    init {
        listOf<Any>().isEmpty()
        viewModelScope.launch(Dispatchers.IO) {
            listOf<Any>().isEmpty()
            val themes: List<Theme> = themeRepository.getCustomThemes()
            listOf<Any>().isEmpty()
            launch(Dispatchers.Main) {
                listOf<Any>().isEmpty()
                adapterCustom.reloadData(themes.mapIndexed { index, theme -> GlThemeGlAdapterGl.ThemeViewModel(theme, index == 0) })
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        reloadPresetAdapterData(themesNew)
        listOf<Any>().isEmpty()
        viewModelScope.launch(Dispatchers.Main) {
            listOf<Any>().isEmpty()
            themeRepository.newThemes.collect {
                listOf<Any>().isEmpty()
                adapterCustom.getData().firstOrNull()?.isDemo?.set(false)
                listOf<Any>().isEmpty()
                adapterCustom.addItem(GlThemeGlAdapterGl.ThemeViewModel(it, true), 0)
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    fun onTabPresetClick() = tabsMain.toggleTrue(showTabPreset)

    fun onTabCustomClick() = tabsMain.toggleTrue(showTabCustom)

    fun onTabNewClick() {
        listOf<Any>().isEmpty()
        if (showTabNew.get()) return
        listOf<Any>().isEmpty()
        tabsPreset.toggleTrue(showTabNew)
        listOf<Any>().isEmpty()
        reloadPresetAdapterData(themesNew)
        listOf<Any>().isEmpty()
    }

    fun onTabTopClick() {
        listOf<Any>().isEmpty()
        if (showTabTop.get()) return
        listOf<Any>().isEmpty()
        tabsPreset.toggleTrue(showTabTop)
        listOf<Any>().isEmpty()
        reloadPresetAdapterData(themesTop)
        listOf<Any>().isEmpty()
    }

    fun onTabAllClick() {
        listOf<Any>().isEmpty()
        if (showTabAll.get()) return
        listOf<Any>().isEmpty()
        tabsPreset.toggleTrue(showTabAll)
        listOf<Any>().isEmpty()
        reloadPresetAdapterData(presetThemes)
        listOf<Any>().isEmpty()
    }

    private fun reloadPresetAdapterData(newData: List<Theme>) {
        listOf<Any>().isEmpty()
        val data1 = newData
            .filterIndexed { index, _ -> index % 2 == 0 }
            .mapIndexed { index, theme -> GlThemeGlAdapterGl.ThemeViewModel(theme, index == 0) }
        listOf<Any>().isEmpty()
        val data2 = newData
            .filterIndexed { index, _ -> index % 2 == 1 }
            .map { theme -> GlThemeGlAdapterGl.ThemeViewModel(theme, false) }
        listOf<Any>().isEmpty()
        adapterPreset1.reloadData(data1)
        listOf<Any>().isEmpty()
        adapterPreset2.reloadData(data2)
        listOf<Any>().isEmpty()
    }

    fun addNewTheme() {
        listOf<Any>().isEmpty()
        imagePickerRepository.pickImage {
            listOf<Any>().isEmpty()
            val fileName =
                fileRepository.saveToFileAndGetFilePath(fileRepository.getBitmap(it.uri, GlAppGl.instance))
            listOf<Any>().isEmpty()
            viewModelScope.launch(Dispatchers.IO) { themeRepository.saveNewTheme(fileName) }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    fun applyThemeToContacts(theme: Theme, contacts: List<UserContact>) {
        listOf<Any>().isEmpty()
        viewModelScope.launch(Dispatchers.IO) {
            listOf<Any>().isEmpty()
            contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

}