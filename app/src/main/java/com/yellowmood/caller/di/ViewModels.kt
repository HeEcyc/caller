package com.yellowmood.caller.di

import com.yellowmood.caller.base.LauncherRegistrator
import com.yellowmood.caller.model.contact.UserContact
import com.yellowmood.caller.model.theme.Theme
import com.yellowmood.caller.ui.call.activity.CallActivityViewModel
import com.yellowmood.caller.ui.call.fragment.CallViewModel
import com.yellowmood.caller.ui.contact.ContactViewModel
import com.yellowmood.caller.ui.contacts.ContactsFragment
import com.yellowmood.caller.ui.contacts.ContactsViewModel
import com.yellowmood.caller.ui.dial.activity.DialActivityViewModel
import com.yellowmood.caller.ui.dial.fragment.DialViewModel
import com.yellowmood.caller.ui.home.HomeViewModel
import com.yellowmood.caller.ui.language.LanguageViewModel
import com.yellowmood.caller.ui.main.MainViewModel
import com.yellowmood.caller.ui.preview.PreviewViewModel
import com.yellowmood.caller.ui.settings.SettingsViewModel
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
