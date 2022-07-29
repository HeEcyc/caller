package com.galaxy.call.di

import com.galaxy.call.base.LauncherRegistrator
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.model.theme.Theme
import com.galaxy.call.ui.call.activity.CallActivityViewModel
import com.galaxy.call.ui.call.fragment.CallViewModel
import com.galaxy.call.ui.contact.ContactViewModel
import com.galaxy.call.ui.contacts.ContactsFragment
import com.galaxy.call.ui.contacts.ContactsViewModel
import com.galaxy.call.ui.dial.activity.DialActivityViewModel
import com.galaxy.call.ui.dial.fragment.DialViewModel
import com.galaxy.call.ui.home.HomeViewModel
import com.galaxy.call.ui.language.LanguageViewModel
import com.galaxy.call.ui.main.MainViewModel
import com.galaxy.call.ui.preview.PreviewViewModel
import com.galaxy.call.ui.settings.SettingsViewModel
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
