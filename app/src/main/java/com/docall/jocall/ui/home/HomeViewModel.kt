package com.docall.jocall.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.docall.jocall.App
import com.docall.jocall.base.BaseViewModel
import com.docall.jocall.model.theme.Theme
import com.docall.jocall.repository.FileRepository
import com.docall.jocall.repository.ImagePickerRepository
import com.docall.jocall.repository.LocaleRepository
import com.docall.jocall.repository.PermissionRepository
import com.docall.jocall.repository.PreferencesRepository
import com.docall.jocall.repository.ThemeRepository
import com.docall.jocall.utils.presetThemes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val themeRepository: ThemeRepository,
    private val imagePickerRepository: ImagePickerRepository,
    val permissionRepository: PermissionRepository,
    private val fileRepository: FileRepository,
    val preferencesRepository: PreferencesRepository,
    val localeRepository: LocaleRepository
) : BaseViewModel() {

    val onThemeClick = MutableLiveData<Theme>()
    val onPreviewClick = MutableLiveData<Theme>()

    val adapterRV = ThemeAdapterRV(::onThemeClick, onPreviewClick::postValue)

    val adapterLanguage = LanguageAdapter(localeRepository) {
        localeRepository.locale = it
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val themes = listOf(
                *themeRepository.getCustomThemes().toTypedArray(),
                *presetThemes.toTypedArray()
            ).map { ThemeAdapterRV.ThemeViewModel(it, it == preferencesRepository.theme) }
            launch(Dispatchers.Main) {
                adapterRV.reloadData(themes)
            }
        }
        viewModelScope.launch(Dispatchers.Main) {
            themeRepository.newThemes.collect {
                adapterRV.addItem(ThemeAdapterRV.ThemeViewModel(it, false), 0)
            }
        }
        adapterLanguage.reloadData(LocaleRepository.Locale.values().toList())
        observe(preferencesRepository.themeObservable) { _, _ ->
            adapterRV.getData().forEach {
                it.isSelected.set(it.theme == preferencesRepository.theme)
            }
        }
    }

    private fun onThemeClick(theme: Theme) = onThemeClick.postValue(theme)

    fun addNewTheme() {
        imagePickerRepository.pickImage {
            val fileName =
                fileRepository.saveToFileAndGetFilePath(fileRepository.getBitmap(it.uri, App.instance))
            viewModelScope.launch(Dispatchers.IO) { themeRepository.saveNewTheme(fileName) }
        }
    }

    fun applyToAll(theme: Theme) {
        preferencesRepository.theme = theme
        adapterRV.getData().forEach { it.isSelected.set(it.theme == theme) }
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