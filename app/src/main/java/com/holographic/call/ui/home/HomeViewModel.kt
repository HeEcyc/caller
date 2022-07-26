package com.holographic.call.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.holographic.call.App
import com.holographic.call.base.BaseViewModel
import com.holographic.call.model.contact.UserContact
import com.holographic.call.model.theme.NewTheme
import com.holographic.call.model.theme.Theme
import com.holographic.call.repository.FileRepository
import com.holographic.call.repository.ImagePickerRepository
import com.holographic.call.repository.PermissionRepository
import com.holographic.call.repository.ThemeRepository
import com.holographic.call.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val themeRepository: ThemeRepository,
    private val imagePickerRepository: ImagePickerRepository,
    val permissionRepository: PermissionRepository,
    private val fileRepository: FileRepository
) : BaseViewModel(), ThemeAdapter.SelectThemeViewModel {

    val needRequestLayout = MutableLiveData<Unit>()
    val addNew = MutableLiveData<Unit>()
    val themeSelected = MutableLiveData<Theme>()

    override val selectedTheme by lazy { ObservableField<Theme>() }

    val adapter = ThemeAdapter(this, ::onThemeClick)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val themes: List<Theme> = themeRepository.getCustomThemes()
            launch(Dispatchers.Main) {
                adapter.reloadData(themes.toMutableList().apply {
                    add(0, NewTheme)
                    addAll(presetThemes)
                })
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            themeRepository.newThemes.collect { adapter.addItem(it, 1) }
        }
        viewModelScope.launch(Dispatchers.Main) {
            themeRepository.deletedThemes.collect {
                adapter.removeItem(adapter.getData().indexOf(it))
            }
        }
        observe(selectedTheme) { _, _ -> needRequestLayout.postValue(Unit) }
    }

    fun addNewTheme() {
        imagePickerRepository.pickImage {
            val fileName =
                fileRepository.saveToFileAndGetFilePath(fileRepository.getBitmap(it.uri, App.instance))
            viewModelScope.launch(Dispatchers.IO) { themeRepository.saveNewTheme(fileName) }
        }
    }

    private fun onThemeClick(theme: Theme) {
        if (theme is NewTheme) {
            addNew.postValue(Unit)
        } else {
            selectedTheme.set(theme)
            themeSelected.postValue(theme)
        }
    }

    fun applyThemeToContacts(contacts: List<UserContact>) {
        val theme = selectedTheme.get() ?: return
        viewModelScope.launch(Dispatchers.IO) {
            contacts.forEach { themeRepository.setContactTheme(it.contactId, theme.backgroundFile) }
        }
    }

}