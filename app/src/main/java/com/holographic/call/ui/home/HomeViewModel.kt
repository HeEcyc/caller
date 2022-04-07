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
import com.holographic.call.utils.themesCats
import com.holographic.call.utils.themesGames
import com.holographic.call.utils.themesMovies
import com.holographic.call.utils.themesPopular
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val themeRepository: ThemeRepository,
    private val imagePickerRepository: ImagePickerRepository,
    val permissionRepository: PermissionRepository,
    private val fileRepository: FileRepository
) : BaseViewModel() {

    val needRequestLayout = MutableLiveData<Unit>()
    val addNew = MutableLiveData<Unit>()
    val themeSelected = MutableLiveData<Theme>()

    val selectedTheme = ObservableField<Theme>()

    val adapterCustom = ThemeAdapter(this, ::onThemeClick)
    val adapterPopular = ThemeAdapter(this, ::onThemeClick)
    val adapterGames = ThemeAdapter(this, ::onThemeClick)
    val adapterCats = ThemeAdapter(this, ::onThemeClick)
    val adapterMovies = ThemeAdapter(this, ::onThemeClick)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val themes: List<Theme> = themeRepository.getCustomThemes()
            launch(Dispatchers.Main) {
                adapterCustom.reloadData(themes.toMutableList().apply { add(0, NewTheme) })
            }
        }
        adapterPopular.reloadData(themesPopular)
        adapterGames.reloadData(themesGames)
        adapterCats.reloadData(themesCats)
        adapterMovies.reloadData(themesMovies)
        viewModelScope.launch(Dispatchers.Main) {
            themeRepository.newThemes.collect { adapterCustom.addItem(it, 1) }
        }
        viewModelScope.launch(Dispatchers.Main) {
            themeRepository.deletedThemes.collect {
                adapterCustom.removeItem(adapterCustom.getData().indexOf(it))
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