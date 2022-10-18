package com.fantasia.telecaller.di

import com.fantasia.telecaller.base.LauncherRegistrator
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.ui.call.activity.CallActivityViewModel
import com.fantasia.telecaller.ui.call.fragment.CallViewModel
import com.fantasia.telecaller.ui.contact.ContactViewModel
import com.fantasia.telecaller.ui.contacts.ContactsFragment
import com.fantasia.telecaller.ui.contacts.ContactsViewModel
import com.fantasia.telecaller.ui.dial.activity.DialActivityViewModel
import com.fantasia.telecaller.ui.dial.fragment.DialViewModel
import com.fantasia.telecaller.ui.home.HomeViewModel
import com.fantasia.telecaller.ui.language.LanguageViewModel
import com.fantasia.telecaller.ui.main.MainViewModel
import com.fantasia.telecaller.ui.preview.PreviewViewModel
import com.fantasia.telecaller.ui.settings.SettingsViewModel
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
