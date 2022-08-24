package com.threed.caller.di

import com.threed.caller.base.LauncherRegistrator
import com.threed.caller.model.contact.UserContact
import com.threed.caller.model.theme.Theme
import com.threed.caller.ui.call.activity.CallActivityViewModel
import com.threed.caller.ui.call.fragment.CallViewModel
import com.threed.caller.ui.contact.ContactViewModel
import com.threed.caller.ui.contacts.ContactsFragment
import com.threed.caller.ui.contacts.ContactsViewModel
import com.threed.caller.ui.dial.activity.DialActivityViewModel
import com.threed.caller.ui.dial.fragment.DialViewModel
import com.threed.caller.ui.home.HomeViewModel
import com.threed.caller.ui.language.LanguageViewModel
import com.threed.caller.ui.main.MainViewModel
import com.threed.caller.ui.preview.PreviewViewModel
import com.threed.caller.ui.settings.SettingsViewModel
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
