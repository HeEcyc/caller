package com.bbbotttixxx.callscreen.di

import com.bbbotttixxx.callscreen.base.LauncherRegistrator
import com.bbbotttixxx.callscreen.model.contact.UserContact
import com.bbbotttixxx.callscreen.ui.call.activity.CallActivityViewModel
import com.bbbotttixxx.callscreen.ui.call.fragment.CallViewModel
import com.bbbotttixxx.callscreen.ui.contacts.ContactsActivity
import com.bbbotttixxx.callscreen.ui.contacts.ContactsViewModel
import com.bbbotttixxx.callscreen.ui.dial.activity.DialActivityViewModel
import com.bbbotttixxx.callscreen.ui.dial.fragment.DialViewModel
import com.bbbotttixxx.callscreen.ui.home.HomeViewModel
import com.bbbotttixxx.callscreen.ui.main.MainViewModel
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