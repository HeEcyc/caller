package com.callerafter.lovelycall.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.callerafter.lovelycall.base.BaseViewModel
import com.callerafter.lovelycall.model.theme.NewTheme
import com.callerafter.lovelycall.model.theme.Theme
import com.callerafter.lovelycall.repository.ImagePickerRepository
import com.callerafter.lovelycall.repository.ThemeRepository
import com.callerafter.lovelycall.utils.themesCats
import com.callerafter.lovelycall.utils.themesGames
import com.callerafter.lovelycall.utils.themesMovies
import com.callerafter.lovelycall.utils.themesPopular
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    val mode: HomeFragment.Mode,
    private val themeRepository: ThemeRepository
) : BaseViewModel() {

    lateinit var imagePickerRepository: ImagePickerRepository

    val onNewThemeClick = MutableLiveData<Unit>()

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
        else -> {}
    }

}