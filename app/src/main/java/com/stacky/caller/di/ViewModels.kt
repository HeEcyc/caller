package com.stacky.caller.di

import com.stacky.caller.base.LauncherRegistrator
import com.stacky.caller.model.contact.UserContact
import com.stacky.caller.model.theme.Theme
import com.stacky.caller.ui.call.activity.CallActivityViewModel
import com.stacky.caller.ui.call.fragment.CallViewModel
import com.stacky.caller.ui.contact.ContactViewModel
import com.stacky.caller.ui.contacts.ContactsFragment
import com.stacky.caller.ui.contacts.ContactsViewModel
import com.stacky.caller.ui.dial.activity.DialActivityViewModel
import com.stacky.caller.ui.dial.fragment.DialViewModel
import com.stacky.caller.ui.home.HomeViewModel
import com.stacky.caller.ui.language.LanguageViewModel
import com.stacky.caller.ui.main.MainViewModel
import com.stacky.caller.ui.preview.PreviewViewModel
import com.stacky.caller.ui.settings.SettingsViewModel
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
