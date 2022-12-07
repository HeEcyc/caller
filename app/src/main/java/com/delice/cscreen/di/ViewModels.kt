package com.delice.cscreen.di

import com.delice.cscreen.base.LauncherRegistrator
import com.delice.cscreen.model.contact.UserContact
import com.delice.cscreen.ui.call.activity.CallActivityViewModel
import com.delice.cscreen.ui.call.fragment.CallViewModel
import com.delice.cscreen.ui.contacts.ContactsActivity
import com.delice.cscreen.ui.contacts.ContactsViewModel
import com.delice.cscreen.ui.dial.activity.DialActivityViewModel
import com.delice.cscreen.ui.dial.fragment.DialViewModel
import com.delice.cscreen.ui.home.HomeViewModel
import com.delice.cscreen.ui.main.MainViewModel
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