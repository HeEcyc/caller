package com.glasserino.caller.di

import com.glasserino.caller.base.LauncherRegistrator
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.model.theme.Theme
import com.glasserino.caller.ui.call.CallActivityViewModel
import com.glasserino.caller.ui.call.fragment.CallViewModel
import com.glasserino.caller.ui.contact.ContactViewModel
import com.glasserino.caller.ui.contacts.ContactsFragment
import com.glasserino.caller.ui.contacts.ContactsViewModel
import com.glasserino.caller.ui.dial.activity.DialActivityViewModel
import com.glasserino.caller.ui.dial.fragment.DialViewModel
import com.glasserino.caller.ui.home.HomeViewModel
import com.glasserino.caller.ui.language.LanguageViewModel
import com.glasserino.caller.ui.main.MainViewModel
import com.glasserino.caller.ui.preview.PreviewViewModel
import com.glasserino.caller.ui.settings.SettingsViewModel
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