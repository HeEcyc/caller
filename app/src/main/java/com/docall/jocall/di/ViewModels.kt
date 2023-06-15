package com.docall.jocall.di

import com.docall.jocall.base.LauncherRegistrator
import com.docall.jocall.model.contact.UserContact
import com.docall.jocall.model.theme.Theme
import com.docall.jocall.ui.call.activity.CallActivityViewModel
import com.docall.jocall.ui.call.fragment.CallViewModel
import com.docall.jocall.ui.contacts.ContactsActivity
import com.docall.jocall.ui.contacts.ContactsViewModel
import com.docall.jocall.ui.dial.activity.DialActivityViewModel
import com.docall.jocall.ui.dial.fragment.DialViewModel
import com.docall.jocall.ui.home.HomeViewModel
import com.docall.jocall.ui.main.MainViewModel
import com.docall.jocall.ui.preview.PreviewViewModel
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
    viewModel { (contact: UserContact, lr: LauncherRegistrator) -> CallViewModel(contact, get(), get(), get { parametersOf(lr) }) }
    viewModel { (theme: Theme) -> PreviewViewModel(theme, get(), get(), get()) }
}