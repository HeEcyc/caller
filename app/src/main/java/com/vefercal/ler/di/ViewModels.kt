package com.vefercal.ler.di

import com.vefercal.ler.base.LauncherRegistrator
import com.vefercal.ler.model.contact.UserContact
import com.vefercal.ler.model.theme.Theme
import com.vefercal.ler.ui.call.activity.CallActivityViewModel
import com.vefercal.ler.ui.call.fragment.CallViewModel
import com.vefercal.ler.ui.contact.ContactViewModel
import com.vefercal.ler.ui.contacts.ContactsFragment
import com.vefercal.ler.ui.contacts.ContactsViewModel
import com.vefercal.ler.ui.dial.activity.DialActivityViewModel
import com.vefercal.ler.ui.dial.fragment.DialViewModel
import com.vefercal.ler.ui.home.HomeViewModel
import com.vefercal.ler.ui.language.LanguageViewModel
import com.vefercal.ler.ui.main.MainViewModel
import com.vefercal.ler.ui.preview.PreviewViewModel
import com.vefercal.ler.ui.settings.SettingsViewModel
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
