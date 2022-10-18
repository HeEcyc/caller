package com.fantasia.telecaller.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasia.telecaller.App
import com.fantasia.telecaller.base.BaseViewModel
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.repository.FileRepository
import com.fantasia.telecaller.repository.ImagePickerRepository
import com.fantasia.telecaller.repository.PermissionRepository
import com.fantasia.telecaller.repository.ThemeRepository
import com.fantasia.telecaller.utils.*
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
    val tabYours = ObservableBoolean(false)
    val tabTop = ObservableBoolean(false)
    val tabCute = ObservableBoolean(false)
    val tabRecommended = ObservableBoolean(false)
    private val tabs = VirtualRadioGroup(tabAll, tabTop, tabCute, tabRecommended, tabYours)

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

    fun onYoursClick() {
        if (tabYours.get()) return
        tabs.toggleTrue(tabYours)
        viewModelScope.launch(Dispatchers.IO) {
            val themes: List<Theme> = themeRepository.getCustomThemes()
            launch(Dispatchers.Main) { adapter.reloadData(themes.toViewModels()) }
        }
    }

    fun onTopClick() {
        if (tabTop.get()) return
        tabs.toggleTrue(tabTop)
        adapter.reloadData(themesTop.toViewModels())
    }

    fun onCuteClick() {
        if (tabCute.get()) return
        tabs.toggleTrue(tabCute)
        adapter.reloadData(themesCute.toViewModels())
    }

    fun onRecommendedClick() {
        if (tabRecommended.get()) return
        tabs.toggleTrue(tabRecommended)
        adapter.reloadData(themesRecommended.toViewModels())
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