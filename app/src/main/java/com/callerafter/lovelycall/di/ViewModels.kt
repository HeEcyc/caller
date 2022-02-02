package com.callerafter.lovelycall.di

import com.callerafter.lovelycall.model.theme.Theme
import com.callerafter.lovelycall.ui.contacts.ContactsFragment
import com.callerafter.lovelycall.ui.contacts.ContactsViewModel
import com.callerafter.lovelycall.ui.crop.CropViewModel
import com.callerafter.lovelycall.ui.home.HomeFragment
import com.callerafter.lovelycall.ui.home.HomeViewModel
import com.callerafter.lovelycall.ui.language.LanguageViewModel
import com.callerafter.lovelycall.ui.main.MainViewModel
import com.callerafter.lovelycall.ui.settings.SettingsViewModel
import com.callerafter.lovelycall.ui.splash.SplashViewModel
import com.callerafter.lovelycall.ui.theme.ThemeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { (mode: HomeFragment.Mode) -> HomeViewModel(mode, get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { LanguageViewModel(get()) }
    viewModel { CropViewModel(get(), get()) }
    viewModel { (mode: ContactsFragment.Mode) -> ContactsViewModel(mode, get()) }
    viewModel { (theme: Theme) -> ThemeViewModel(theme, get(), get(), get()) }
}