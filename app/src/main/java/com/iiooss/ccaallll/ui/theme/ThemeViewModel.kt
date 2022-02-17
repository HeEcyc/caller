package com.iiooss.ccaallll.ui.theme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iiooss.ccaallll.App
import com.iiooss.ccaallll.base.BaseViewModel
import com.iiooss.ccaallll.model.theme.NewTheme
import com.iiooss.ccaallll.model.theme.Theme
import com.iiooss.ccaallll.repository.FileRepository
import com.iiooss.ccaallll.repository.ImagePickerRepository
import com.iiooss.ccaallll.repository.PermissionRepository
import com.iiooss.ccaallll.repository.ThemeRepository
import com.iiooss.ccaallll.utils.themesCats
import com.iiooss.ccaallll.utils.themesGames
import com.iiooss.ccaallll.utils.themesMovies
import com.iiooss.ccaallll.utils.themesPopular
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeViewModel(
    val permissionRepository: PermissionRepository,
    private val themeRepository: ThemeRepository,
    private val imagePickerRepository: ImagePickerRepository,
    private val fileRepository: FileRepository
) : BaseViewModel() {

    val addNewTheme = MutableLiveData<Unit>()
    val setTheme = MutableLiveData<Theme>()

    val adapterCustom = ThemeAdapter(::onThemeClick)
    val adapterPopular = ThemeAdapter(::onThemeClick)
    val adapterGames = ThemeAdapter(::onThemeClick)
    val adapterCats = ThemeAdapter(::onThemeClick)
    val adapterMovies = ThemeAdapter(::onThemeClick)

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
    }

    private fun onThemeClick(theme: Theme) = if (theme is NewTheme)
        addNewTheme.postValue(Unit)
    else
        setTheme.postValue(theme)

    fun addNewTheme() {
        imagePickerRepository.pickImage {
            val fileName =
                fileRepository.saveToFileAndGetFilePath(fileRepository.getBitmap(it.uri, App.instance))
            viewModelScope.launch(Dispatchers.IO) { themeRepository.saveNewTheme(fileName) }
        }
    }

}