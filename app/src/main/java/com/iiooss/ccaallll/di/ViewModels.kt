package com.iiooss.ccaallll.di

import com.iiooss.ccaallll.base.LauncherRegistrator
import com.iiooss.ccaallll.model.contact.UserContact
import com.iiooss.ccaallll.ui.call.CallActivityViewModel
import com.iiooss.ccaallll.ui.call.fragment.CallViewModel
import com.iiooss.ccaallll.ui.contact.ContactViewModel
import com.iiooss.ccaallll.ui.contacts.ContactsFragment
import com.iiooss.ccaallll.ui.contacts.ContactsViewModel
import com.iiooss.ccaallll.ui.dial.activity.DialActivityViewModel
import com.iiooss.ccaallll.ui.dial.fragment.DialViewModel
import com.iiooss.ccaallll.ui.home.HomeViewModel
import com.iiooss.ccaallll.ui.language.LanguageViewModel
import com.iiooss.ccaallll.ui.main.MainViewModel
import com.iiooss.ccaallll.ui.settings.SettingsViewModel
import com.iiooss.ccaallll.ui.theme.ThemeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: LauncherRegistrator) -> MainViewModel(get(), get { parametersOf(lr) }) }
    viewModel { (lr: LauncherRegistrator) -> HomeViewModel(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> SettingsViewModel(get(), get { parametersOf(lr) }, get()) }
    viewModel { LanguageViewModel(get()) }
    viewModel { (mode: ContactsFragment.Mode, lr: LauncherRegistrator) -> ContactsViewModel(mode, get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> ContactViewModel(contact, get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> ThemeViewModel(get { parametersOf(lr) }, get(), get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> DialViewModel(get { parametersOf(lr) }) }
    viewModel { DialActivityViewModel(get()) }
    viewModel { (lr: LauncherRegistrator) -> CallActivityViewModel(get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> CallViewModel(contact, get(), get(), get(), get { parametersOf(lr) }) }
}