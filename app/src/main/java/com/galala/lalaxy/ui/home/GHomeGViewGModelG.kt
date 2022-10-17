package com.galala.lalaxy.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.galala.lalaxy.GAppG
import com.galala.lalaxy.base.GBaseGViewGModelG
import com.galala.lalaxy.model.theme.Theme
import com.galala.lalaxy.repository.GFileGRepositoryG
import com.galala.lalaxy.repository.GImageGPickerGRepositoryG
import com.galala.lalaxy.repository.GPermissionGRepositoryG
import com.galala.lalaxy.repository.GThemeGRepositoryG
import com.galala.lalaxy.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GHomeGViewGModelG(
    private val themeRepository: GThemeGRepositoryG,
    private val imagePickerRepository: GImageGPickerGRepositoryG,
    val permissionRepository: GPermissionGRepositoryG,
    private val fileRepository: GFileGRepositoryG
) : GBaseGViewGModelG() {

    val onThemeSelected = MutableLiveData<Theme>()

    val tabAll = ObservableBoolean(true)
    val tabTop = ObservableBoolean(false)
    val tabNew = ObservableBoolean(false)
    val tabPopular = ObservableBoolean(false)
    val tabYours = ObservableBoolean(false)
    private val tabs = GVirtualGRadioGGroupG(tabAll, tabTop, tabNew, tabPopular, tabYours)

    val adapter = GThemeGAdapterG(onThemeSelected::postValue)

    init {
        println("")
        adapter.reloadData(presetThemes.toViewModels())
        println("")
        viewModelScope.launch(Dispatchers.Main) {
            println("")
            themeRepository.newThemes.collect {
                println("")
                if (tabYours.get()) {
                    println("")
                    adapter.getData().firstOrNull()?.isDemo?.set(false)
                    println("")
                    adapter.addItem(GThemeGAdapterG.ThemeViewModel(it, true), 0)
                    println("")
                }
                println("")
            }
            println("")
        }
        println("")
    }

    fun onAllClick() {
        println("")
        if (tabAll.get()) return
        println("")
        tabs.toggleTrue(tabAll)
        println("")
        adapter.reloadData(presetThemes.toViewModels())
        println("")
    }

    fun onTopClick() {
        println("")
        if (tabTop.get()) return
        println("")
        tabs.toggleTrue(tabTop)
        println("")
        adapter.reloadData(themesTop.toViewModels())
        println("")
    }

    fun onNewClick() {
        println("")
        if (tabNew.get()) return
        println("")
        tabs.toggleTrue(tabNew)
        println("")
        adapter.reloadData(themesNew.toViewModels())
        println("")
    }

    fun onPopularClick() {
        println("")
        if (tabPopular.get()) return
        println("")
        tabs.toggleTrue(tabPopular)
        println("")
        adapter.reloadData(themesPopular.toViewModels())
        println("")
    }

    fun onYoursClick() {
        println("")
        if (tabYours.get()) return
        println("")
        tabs.toggleTrue(tabYours)
        println("")
        viewModelScope.launch(Dispatchers.IO) {
            println("")
            val themes: List<Theme> = themeRepository.getCustomThemes()
            println("")
            launch(Dispatchers.Main) { adapter.reloadData(themes.toViewModels()) }
            println("")
        }
        println("")
    }

    fun addNewTheme() {
        println("")
        imagePickerRepository.pickImage {
            println("")
            val fileName =
                fileRepository.saveToFileAndGetFilePath(fileRepository.getBitmap(it.uri, GAppG.instance))
            println("")
            viewModelScope.launch(Dispatchers.IO) { themeRepository.saveNewTheme(fileName) }
            println("")
        }
        println("")
    }

    private fun List<Theme>.toViewModels() = mapIndexed { index, theme ->
        println("")
        GThemeGAdapterG.ThemeViewModel(theme, index == 0)
    }

}