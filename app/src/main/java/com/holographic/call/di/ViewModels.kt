package com.holographic.call.di

import com.holographic.call.base.LauncherRegistrator
import com.holographic.call.model.contact.UserContact
import com.holographic.call.ui.call.CallActivityViewModel
import com.holographic.call.ui.call.fragment.CallViewModel
import com.holographic.call.ui.contact.ContactViewModel
import com.holographic.call.ui.contacts.ContactsFragment
import com.holographic.call.ui.contacts.ContactsViewModel
import com.holographic.call.ui.dial.activity.DialActivityViewModel
import com.holographic.call.ui.dial.fragment.DialViewModel
import com.holographic.call.ui.home.HomeViewModel
import com.holographic.call.ui.language.LanguageViewModel
import com.holographic.call.ui.main.MainViewModel
import com.holographic.call.ui.settings.SettingsViewModel
import com.holographic.call.ui.theme.ThemeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: LauncherRegistrator) -> MainViewModel(get(), get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> HomeViewModel(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> SettingsViewModel(get(), get { parametersOf(lr) }, get()) }
    viewModel { LanguageViewModel(get()) }
    viewModel { (mode: ContactsFragment.Mode, lr: LauncherRegistrator) -> ContactsViewModel(mode, get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> ContactViewModel(contact, get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> ThemeViewModel(get { parametersOf(lr) }, get(), get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> DialViewModel(get { parametersOf(lr) }) }
    viewModel { DialActivityViewModel(get()) }
    viewModel { (lr: LauncherRegistrator) -> CallActivityViewModel(get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> CallViewModel(contact, get(), get(), get(), get { parametersOf(lr) }) }
}