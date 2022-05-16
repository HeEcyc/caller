package com.glass.call.di

import com.glass.call.base.LauncherRegistrator
import com.glass.call.model.contact.UserContact
import com.glass.call.model.theme.Theme
import com.glass.call.ui.call.CallActivityViewModel
import com.glass.call.ui.call.fragment.CallViewModel
import com.glass.call.ui.contact.ContactViewModel
import com.glass.call.ui.contacts.ContactsFragment
import com.glass.call.ui.contacts.ContactsViewModel
import com.glass.call.ui.dial.activity.DialActivityViewModel
import com.glass.call.ui.dial.fragment.DialViewModel
import com.glass.call.ui.home.HomeViewModel
import com.glass.call.ui.language.LanguageViewModel
import com.glass.call.ui.main.MainViewModel
import com.glass.call.ui.preview.PreviewViewModel
import com.glass.call.ui.settings.SettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: LauncherRegistrator) -> MainViewModel(get(), get { parametersOf(lr) }) }
    viewModel { (lr: LauncherRegistrator) -> HomeViewModel(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get()) }
    viewModel { (t: Theme) -> PreviewViewModel(t, get(), get(), get()) }
    viewModel { (mode: ContactsFragment.Mode, lr: LauncherRegistrator) -> ContactsViewModel(mode, get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> ContactViewModel(contact, get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> SettingsViewModel(get(), get { parametersOf(lr) }, get()) }
    viewModel { LanguageViewModel(get()) }
    viewModel { (lr: LauncherRegistrator) -> DialViewModel(get { parametersOf(lr) }) }
    viewModel { DialActivityViewModel(get()) }
    viewModel { (lr: LauncherRegistrator) -> CallActivityViewModel(get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> CallViewModel(contact, get(), get(), get(), get { parametersOf(lr) }) }
}