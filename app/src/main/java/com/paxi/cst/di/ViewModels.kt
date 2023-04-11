package com.paxi.cst.di

import com.paxi.cst.base.LauncherRegistrator
import com.paxi.cst.model.contact.UserContact
import com.paxi.cst.ui.call.activity.CallActivityViewModel
import com.paxi.cst.ui.call.fragment.CallViewModel
import com.paxi.cst.ui.contacts.ContactsActivity
import com.paxi.cst.ui.contacts.ContactsViewModel
import com.paxi.cst.ui.dial.activity.DialActivityViewModel
import com.paxi.cst.ui.dial.fragment.DialViewModel
import com.paxi.cst.ui.home.HomeViewModel
import com.paxi.cst.ui.main.MainViewModel
import com.paxi.cst.ui.settings.SettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: LauncherRegistrator) -> MainViewModel(get(), get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> HomeViewModel(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get(), get(), get()) }
    viewModel { (mode: ContactsActivity.Mode, lr: LauncherRegistrator) -> ContactsViewModel(mode, get(), get(), get { parametersOf(lr) }) }
    viewModel { (lr: LauncherRegistrator) -> DialViewModel(get { parametersOf(lr) }) }
    viewModel { DialActivityViewModel(get()) }
    viewModel { (lr: LauncherRegistrator) -> CallActivityViewModel(get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> CallViewModel(contact, get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (lr: LauncherRegistrator) -> SettingsViewModel(get { parametersOf(lr) }, get(), get()) }
}