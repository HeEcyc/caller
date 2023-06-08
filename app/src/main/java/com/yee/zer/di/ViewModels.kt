package com.yee.zer.di

import com.yee.zer.base.LauncherRegistrator
import com.yee.zer.model.contact.UserContact
import com.yee.zer.ui.call.activity.CallActivityViewModel
import com.yee.zer.ui.call.fragment.CallViewModel
import com.yee.zer.ui.contacts.ContactsActivity
import com.yee.zer.ui.contacts.ContactsViewModel
import com.yee.zer.ui.dial.activity.DialActivityViewModel
import com.yee.zer.ui.dial.fragment.DialViewModel
import com.yee.zer.ui.home.HomeViewModel
import com.yee.zer.ui.main.MainViewModel
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