package com.megaaa.caaall.di

import com.megaaa.caaall.model.contact.UserContact
import com.megaaa.caaall.model.theme.Theme
import com.megaaa.caaall.ui.call.CallActivityViewModel
import com.megaaa.caaall.ui.call.fragment.CallViewModel
import com.megaaa.caaall.ui.contact.ContactViewModel
import com.megaaa.caaall.ui.contacts.ContactsFragment
import com.megaaa.caaall.ui.contacts.ContactsViewModel
import com.megaaa.caaall.ui.crop.CropViewModel
import com.megaaa.caaall.ui.dial.DialViewModel
import com.megaaa.caaall.ui.home.HomeFragment
import com.megaaa.caaall.ui.home.HomeViewModel
import com.megaaa.caaall.ui.language.LanguageViewModel
import com.megaaa.caaall.ui.main.MainViewModel
import com.megaaa.caaall.ui.settings.SettingsViewModel
import com.megaaa.caaall.ui.splash.SplashViewModel
import com.megaaa.caaall.ui.theme.ThemeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { (mode: HomeFragment.Mode) -> HomeViewModel(mode, get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { LanguageViewModel(get()) }
    viewModel { CropViewModel(get(), get()) }
    viewModel { (mode: ContactsFragment.Mode) -> ContactsViewModel(mode, get()) }
    viewModel { (theme: Theme) -> ThemeViewModel(theme, get(), get(), get()) }
    viewModel { (contact: UserContact) -> ContactViewModel(contact, get(), get()) }
    viewModel { DialViewModel(get()) }
    viewModel { CallActivityViewModel(get(), get(), get()) }
    viewModel { (contact: UserContact) -> CallViewModel(contact, get(), get(), get()) }
}