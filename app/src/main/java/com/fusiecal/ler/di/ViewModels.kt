package com.fusiecal.ler.di

import com.fusiecal.ler.base.LauncherRegistrator
import com.fusiecal.ler.model.contact.UserContact
import com.fusiecal.ler.ui.call.activity.CallActivityViewModel
import com.fusiecal.ler.ui.call.fragment.CallViewModel
import com.fusiecal.ler.ui.contacts.ContactsActivity
import com.fusiecal.ler.ui.contacts.ContactsViewModel
import com.fusiecal.ler.ui.dial.activity.DialActivityViewModel
import com.fusiecal.ler.ui.dial.fragment.DialViewModel
import com.fusiecal.ler.ui.home.HomeViewModel
import com.fusiecal.ler.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: LauncherRegistrator) -> MainViewModel(get(), get { parametersOf(lr) }, get()) }
    viewModel { (lr: LauncherRegistrator) -> HomeViewModel(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get(), get(), get(), get()) }
    viewModel { (mode: ContactsActivity.Mode, lr: LauncherRegistrator) -> ContactsViewModel(mode, get(), get(), get { parametersOf(lr) }) }
    viewModel { (lr: LauncherRegistrator) -> DialViewModel(get { parametersOf(lr) }) }
    viewModel { DialActivityViewModel(get()) }
    viewModel { (lr: LauncherRegistrator) -> CallActivityViewModel(get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> CallViewModel(contact, get(), get(), get(), get { parametersOf(lr) }) }
}