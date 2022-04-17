package com.maxios.maxcall.di

import com.maxios.maxcall.base.LauncherRegistrator
import com.maxios.maxcall.model.contact.UserContact
import com.maxios.maxcall.ui.call.CallActivityViewModel
import com.maxios.maxcall.ui.call.fragment.CallViewModel
import com.maxios.maxcall.ui.contact.ContactViewModel
import com.maxios.maxcall.ui.contacts.ContactsFragment
import com.maxios.maxcall.ui.contacts.ContactsViewModel
import com.maxios.maxcall.ui.dial.activity.DialActivityViewModel
import com.maxios.maxcall.ui.dial.fragment.DialViewModel
import com.maxios.maxcall.ui.home.HomeViewModel
import com.maxios.maxcall.ui.language.LanguageViewModel
import com.maxios.maxcall.ui.main.MainViewModel
import com.maxios.maxcall.ui.settings.SettingsViewModel
import com.maxios.maxcall.ui.theme.ThemeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: LauncherRegistrator) -> MainViewModel(get(), get { parametersOf(lr) }) }
    viewModel { (lr: LauncherRegistrator) -> HomeViewModel(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> SettingsViewModel(get(), get { parametersOf(lr) }, get()) }
    viewModel { LanguageViewModel(get()) }
    viewModel { (mode: ContactsFragment.Mode, lr: LauncherRegistrator) -> ContactsViewModel(mode, get(), get(), get { parametersOf(lr) }) }
    viewModel { DialActivityViewModel(get()) }
    viewModel { (lr: LauncherRegistrator) -> DialViewModel(get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> ContactViewModel(contact, get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> ThemeViewModel(get { parametersOf(lr) }, get(), get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> CallActivityViewModel(get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> CallViewModel(contact, get(), get(), get(), get { parametersOf(lr) }) }
}