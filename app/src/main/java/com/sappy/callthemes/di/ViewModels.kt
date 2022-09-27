package com.sappy.callthemes.di

import com.sappy.callthemes.base.LauncherRegistrator
import com.sappy.callthemes.model.contact.UserContact
import com.sappy.callthemes.model.theme.Theme
import com.sappy.callthemes.ui.call.activity.CallActivityViewModel
import com.sappy.callthemes.ui.call.fragment.CallViewModel
import com.sappy.callthemes.ui.contact.ContactViewModel
import com.sappy.callthemes.ui.contacts.ContactsFragment
import com.sappy.callthemes.ui.contacts.ContactsViewModel
import com.sappy.callthemes.ui.dial.activity.DialActivityViewModel
import com.sappy.callthemes.ui.dial.fragment.DialViewModel
import com.sappy.callthemes.ui.home.HomeViewModel
import com.sappy.callthemes.ui.language.LanguageViewModel
import com.sappy.callthemes.ui.main.MainViewModel
import com.sappy.callthemes.ui.preview.PreviewViewModel
import com.sappy.callthemes.ui.settings.SettingsViewModel
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
