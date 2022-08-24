package com.threed.caller.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.threed.caller.App
import com.threed.caller.base.BaseViewModel
import com.threed.caller.model.contact.UserContact
import com.threed.caller.model.theme.Theme
import com.threed.caller.repository.FileRepository
import com.threed.caller.repository.ImagePickerRepository
import com.threed.caller.repository.PermissionRepository
import com.threed.caller.repository.ThemeRepository
import com.threed.caller.utils.*
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

    val tabAll = ObservableBoolean(true)
    val tabTop = ObservableBoolean(false)
    val tabNew = ObservableBoolean(false)
    val tabPopular = ObservableBoolean(false)
    val tabYours = ObservableBoolean(false)
    private val tabs = VirtualRadioGroup(tabAll, tabTop, tabNew, tabPopular, tabYours)

    val adapter = ThemeAdapter(onThemeSelected::postValue)

    init {
        adapter.reloadData(presetThemes.toViewModels())
        viewModelScope.launch(Dispatchers.Main) {
            themeRepository.newThemes.collect {
                if (tabYours.get()) {
                    adapter.getData().firstOrNull()?.isDemo?.set(false)
                    adapter.addItem(ThemeAdapter.ThemeViewModel(it, true), 0)
                }
            }
        }
    }

    fun onAllClick() {
        if (tabAll.get()) return
        tabs.toggleTrue(tabAll)
        adapter.reloadData(presetThemes.toViewModels())
    }

    fun onTopClick() {
        if (tabTop.get()) return
        tabs.toggleTrue(tabTop)
        adapter.reloadData(themesTop.toViewModels())
    }

    fun onNewClick() {
        if (tabNew.get()) return
        tabs.toggleTrue(tabNew)
        adapter.reloadData(themesNew.toViewModels())
    }

    fun onPopularClick() {
        if (tabPopular.get()) return
        tabs.toggleTrue(tabPopular)
        adapter.reloadData(themesRecommended.toViewModels())
    }

    fun onYoursClick() {
        if (tabYours.get()) return
        tabs.toggleTrue(tabYours)
        viewModelScope.launch(Dispatchers.IO) {
            val themes: List<Theme> = themeRepository.getCustomThemes()
            launch(Dispatchers.Main) { adapter.reloadData(themes.toViewModels()) }
        }
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

    private fun List<Theme>.toViewModels() = mapIndexed { index, theme ->
        ThemeAdapter.ThemeViewModel(theme, index == 0)
    }

}