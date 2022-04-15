package com.maxios.maxcall.di

import com.maxios.maxcall.base.LauncherRegistrator
import com.maxios.maxcall.ui.home.HomeViewModel
import com.maxios.maxcall.ui.language.LanguageViewModel
import com.maxios.maxcall.ui.main.MainViewModel
import com.maxios.maxcall.ui.settings.SettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: LauncherRegistrator) -> MainViewModel(get(), get { parametersOf(lr) }) }
    viewModel { (lr: LauncherRegistrator) -> HomeViewModel(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> SettingsViewModel(get(), get { parametersOf(lr) }, get()) }
    viewModel { LanguageViewModel(get()) }
}