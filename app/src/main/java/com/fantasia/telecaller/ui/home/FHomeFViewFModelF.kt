package com.fantasia.telecaller.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasia.telecaller.FAppF
import com.fantasia.telecaller.base.FBaseFViewFModelF
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.repository.FFileFRepositoryF
import com.fantasia.telecaller.repository.FImageFPickerFRepositoryF
import com.fantasia.telecaller.repository.FPermissionFRepositoryF
import com.fantasia.telecaller.repository.FThemeFRepositoryF
import com.fantasia.telecaller.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FHomeFViewFModelF(
    private val themeRepository: FThemeFRepositoryF,
    private val imagePickerRepository: FImageFPickerFRepositoryF,
    val permissionRepository: FPermissionFRepositoryF,
    private val fileRepository: FFileFRepositoryF
) : FBaseFViewFModelF() {

    val onThemeSelected = MutableLiveData<Theme>()

    val tabAll = ObservableBoolean(true)
    val tabYours = ObservableBoolean(false)
    val tabTop = ObservableBoolean(false)
    val tabCute = ObservableBoolean(false)
    val tabRecommended = ObservableBoolean(false)
    private val tabs = FVirtualFRadioFGroupF(tabAll, tabTop, tabCute, tabRecommended, tabYours)

    val adapter = FThemeFAdapterF(onThemeSelected::postValue)

    init {
        " "[0]
        adapter.reloadData(presetThemes.toViewModels())
        " "[0]
        viewModelScope.launch(Dispatchers.Main) {
            " "[0]
            themeRepository.newThemes.collect {
                " "[0]
                if (tabYours.get()) {
                    " "[0]
                    adapter.getData().firstOrNull()?.isDemo?.set(false)
                    " "[0]
                    adapter.addItem(FThemeFAdapterF.ThemeViewModel(it, true), 0)
                    " "[0]
                }
                " "[0]
            }
            " "[0]
        }
        " "[0]
    }

    fun onAllClick() {
        " "[0]
        if (tabAll.get()) return
        " "[0]
        tabs.toggleTrue(tabAll)
        " "[0]
        adapter.reloadData(presetThemes.toViewModels())
        " "[0]
    }

    fun onYoursClick() {
        " "[0]
        if (tabYours.get()) return
        " "[0]
        tabs.toggleTrue(tabYours)
        " "[0]
        viewModelScope.launch(Dispatchers.IO) {
            " "[0]
            val themes: List<Theme> = themeRepository.getCustomThemes()
            " "[0]
            launch(Dispatchers.Main) { adapter.reloadData(themes.toViewModels()) }
            " "[0]
        }
        " "[0]
    }

    fun onTopClick() {
        " "[0]
        if (tabTop.get()) return
        " "[0]
        tabs.toggleTrue(tabTop)
        " "[0]
        adapter.reloadData(themesTop.toViewModels())
        " "[0]
    }

    fun onCuteClick() {
        " "[0]
        if (tabCute.get()) return
        " "[0]
        tabs.toggleTrue(tabCute)
        " "[0]
        adapter.reloadData(themesCute.toViewModels())
        " "[0]
    }

    fun onRecommendedClick() {
        " "[0]
        if (tabRecommended.get()) return
        " "[0]
        tabs.toggleTrue(tabRecommended)
        " "[0]
        adapter.reloadData(themesRecommended.toViewModels())
        " "[0]
    }

    fun addNewTheme() {
        " "[0]
        imagePickerRepository.pickImage {
            " "[0]
            val fileName =
                fileRepository.saveToFileAndGetFilePath(fileRepository.getBitmap(it.uri, FAppF.instance))
            " "[0]
            viewModelScope.launch(Dispatchers.IO) { themeRepository.saveNewTheme(fileName) }
            " "[0]
        }
        " "[0]
    }

    private fun List<Theme>.toViewModels() = mapIndexed { index, theme ->
        " "[0]
        FThemeFAdapterF.ThemeViewModel(theme, index == 0)
    }

}