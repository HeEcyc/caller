package com.galaxy.call.di

import com.galaxy.call.base.GLauncherGRegistratorG
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.model.theme.Theme
import com.galaxy.call.ui.call.activity.GCallGActivityGViewGModelG
import com.galaxy.call.ui.call.fragment.GCallGViewGModelG
import com.galaxy.call.ui.contact.GContactGViewGModelG
import com.galaxy.call.ui.contacts.GContactsGFragmentG
import com.galaxy.call.ui.contacts.GContactsGViewGModelG
import com.galaxy.call.ui.dial.activity.GDialGActivityGViewGModelG
import com.galaxy.call.ui.dial.fragment.GDialGViewGModelG
import com.galaxy.call.ui.home.GHomeGViewGModelG
import com.galaxy.call.ui.language.GLanguageGViewGModelG
import com.galaxy.call.ui.main.GMainGViewGModelG
import com.galaxy.call.ui.preview.GPreviewGViewGModelG
import com.galaxy.call.ui.settings.GSettingsGViewGModelG
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: GLauncherGRegistratorG) -> GMainGViewGModelG(get(), get { parametersOf(lr) }, get()) }
    viewModel { (lr: GLauncherGRegistratorG) -> GHomeGViewGModelG(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get()) }
    viewModel { (lr: GLauncherGRegistratorG) -> GSettingsGViewGModelG(get(), get { parametersOf(lr) }, get()) }
    viewModel { GLanguageGViewGModelG(get()) }
    viewModel { (mode: GContactsGFragmentG.Mode, lr: GLauncherGRegistratorG) -> GContactsGViewGModelG(mode, get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: GLauncherGRegistratorG) -> GContactGViewGModelG(contact, get { parametersOf(lr) }, get()) }
    viewModel { (t: Theme) -> GPreviewGViewGModelG(t, get(), get(), get()) }
    viewModel { (lr: GLauncherGRegistratorG) -> GDialGViewGModelG(get { parametersOf(lr) }) }
    viewModel { GDialGActivityGViewGModelG(get()) }
    viewModel { (lr: GLauncherGRegistratorG) -> GCallGActivityGViewGModelG(get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: GLauncherGRegistratorG) -> GCallGViewGModelG(contact, get(), get(), get(), get { parametersOf(lr) }) }

}
