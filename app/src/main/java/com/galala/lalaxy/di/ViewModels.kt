package com.galala.lalaxy.di

import com.galala.lalaxy.base.GLauncherGRegistratorG
import com.galala.lalaxy.model.contact.UserContact
import com.galala.lalaxy.model.theme.Theme
import com.galala.lalaxy.ui.call.activity.GCallGActivityGViewGModelG
import com.galala.lalaxy.ui.call.fragment.GCallGViewGModelG
import com.galala.lalaxy.ui.contact.GContactGViewGModelG
import com.galala.lalaxy.ui.contacts.GContactsGFragmentG
import com.galala.lalaxy.ui.contacts.GContactsGViewGModelG
import com.galala.lalaxy.ui.dial.activity.GDialGActivityGViewGModelG
import com.galala.lalaxy.ui.dial.fragment.GDialGViewGModelG
import com.galala.lalaxy.ui.home.GHomeGViewGModelG
import com.galala.lalaxy.ui.language.GLanguageGViewGModelG
import com.galala.lalaxy.ui.main.GMainGViewGModelG
import com.galala.lalaxy.ui.preview.GPreviewGViewGModelG
import com.galala.lalaxy.ui.settings.GSettingsGViewGModelG
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
