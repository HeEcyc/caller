package com.roobcall.themes.di

import com.roobcall.themes.base.LauncherRegistrator
import com.roobcall.themes.model.contact.UserContact
import com.roobcall.themes.model.theme.Theme
import com.roobcall.themes.ui.call.activity.CallActivityViewModel
import com.roobcall.themes.ui.call.fragment.CallViewModel
import com.roobcall.themes.ui.contact.ContactViewModel
import com.roobcall.themes.ui.contacts.ContactsFragment
import com.roobcall.themes.ui.contacts.ContactsViewModel
import com.roobcall.themes.ui.dial.activity.DialActivityViewModel
import com.roobcall.themes.ui.dial.fragment.DialViewModel
import com.roobcall.themes.ui.home.HomeViewModel
import com.roobcall.themes.ui.language.LanguageViewModel
import com.roobcall.themes.ui.main.MainViewModel
import com.roobcall.themes.ui.preview.PreviewViewModel
import com.roobcall.themes.ui.settings.SettingsViewModel
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
    viewModel { (t: Theme) -> PreviewViewModel(t, get(), get(), get()) }
    viewModel { (lr: LauncherRegistrator) -> DialViewModel(get { parametersOf(lr) }) }
    viewModel { DialActivityViewModel(get()) }
    viewModel { (lr: LauncherRegistrator) -> CallActivityViewModel(get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> CallViewModel(contact, get(), get(), get(), get { parametersOf(lr) }) }

}
