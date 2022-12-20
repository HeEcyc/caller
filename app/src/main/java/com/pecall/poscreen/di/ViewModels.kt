package com.pecall.poscreen.di

import com.pecall.poscreen.base.LauncherRegistrator
import com.pecall.poscreen.model.contact.UserContact
import com.pecall.poscreen.ui.call.activity.CallActivityViewModel
import com.pecall.poscreen.ui.call.fragment.CallViewModel
import com.pecall.poscreen.ui.contacts.ContactsActivity
import com.pecall.poscreen.ui.contacts.ContactsViewModel
import com.pecall.poscreen.ui.dial.activity.DialActivityViewModel
import com.pecall.poscreen.ui.dial.fragment.DialViewModel
import com.pecall.poscreen.ui.home.HomeViewModel
import com.pecall.poscreen.ui.main.MainViewModel
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