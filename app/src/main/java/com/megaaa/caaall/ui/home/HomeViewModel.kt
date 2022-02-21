package com.megaaa.caaall.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.megaaa.caaall.base.BaseViewModel
import com.megaaa.caaall.model.theme.NewTheme
import com.megaaa.caaall.model.theme.Theme
import com.megaaa.caaall.repository.ImagePickerRepository
import com.megaaa.caaall.repository.PreferencesRepository
import com.megaaa.caaall.repository.ThemeRepository
import com.megaaa.caaall.utils.themesCats
import com.megaaa.caaall.utils.themesGames
import com.megaaa.caaall.utils.themesMovies
import com.megaaa.caaall.utils.themesPopular
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    val mode: HomeFragment.Mode,
    private val themeRepository: ThemeRepository,
    val preferencesRepository: PreferencesRepository
) : BaseViewModel() {

    lateinit var imagePickerRepository: ImagePickerRepository

    val onNewThemeClick = MutableLiveData<Unit>()
    val onThemeClick = MutableLiveData<Theme>()

    val adapterCustom = ThemeAdapter(::onThemeClick)
    val adapterPopular = ThemeAdapter(::onThemeClick, true)
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

    private fun onThemeClick(theme: Theme) = when {
        theme is NewTheme -> onNewThemeClick.postValue(Unit)
        else -> onThemeClick.postValue(theme)
    }

}