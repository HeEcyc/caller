package com.fancy.call.di

import com.fancy.call.base.LauncherRegistrator
import com.fancy.call.model.contact.UserContact
import com.fancy.call.model.theme.Theme
import com.fancy.call.ui.call.activity.CallActivityViewModel
import com.fancy.call.ui.call.fragment.CallViewModel
import com.fancy.call.ui.contact.ContactViewModel
import com.fancy.call.ui.contacts.ContactsFragment
import com.fancy.call.ui.contacts.ContactsViewModel
import com.fancy.call.ui.dial.activity.DialActivityViewModel
import com.fancy.call.ui.dial.fragment.DialViewModel
import com.fancy.call.ui.home.HomeViewModel
import com.fancy.call.ui.language.LanguageViewModel
import com.fancy.call.ui.main.MainViewModel
import com.fancy.call.ui.preview.PreviewViewModel
import com.fancy.call.ui.settings.SettingsViewModel
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
