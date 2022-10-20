package com.glasserino.caller.di

import com.glasserino.caller.base.GlLauncherGlRegistratorGl
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.model.theme.Theme
import com.glasserino.caller.ui.call.GlCallGlActivityGlViewGlModelGL
import com.glasserino.caller.ui.call.fragment.GlCallGlViewGlModelGl
import com.glasserino.caller.ui.contact.GlContactGlViewGLModelGL
import com.glasserino.caller.ui.contacts.GlContactsGlFragmentGl
import com.glasserino.caller.ui.contacts.GlContactsGlViewGlModelGl
import com.glasserino.caller.ui.dial.activity.GlDialGlActivityGLViewGLModelGL
import com.glasserino.caller.ui.dial.fragment.GlDialGLViewGLModelGL
import com.glasserino.caller.ui.home.GlHomeGlViewGlModelGl
import com.glasserino.caller.ui.language.GlLanguageGlViewGlModelGl
import com.glasserino.caller.ui.main.GlMainGlViewGlModelGl
import com.glasserino.caller.ui.preview.GlPreviewGlViewGlModelGl
import com.glasserino.caller.ui.settings.GlSettingsGlViewGlModelGl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: GlLauncherGlRegistratorGl) -> GlMainGlViewGlModelGl(get(), get { parametersOf(lr) }) }
    viewModel { (lr: GlLauncherGlRegistratorGl) -> GlHomeGlViewGlModelGl(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get()) }
    viewModel { (t: Theme) -> GlPreviewGlViewGlModelGl(t, get(), get(), get()) }
    viewModel { (mode: GlContactsGlFragmentGl.Mode, lr: GlLauncherGlRegistratorGl) -> GlContactsGlViewGlModelGl(mode, get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: GlLauncherGlRegistratorGl) -> GlContactGlViewGLModelGL(contact, get { parametersOf(lr) }, get()) }
    viewModel { (lr: GlLauncherGlRegistratorGl) -> GlSettingsGlViewGlModelGl(get(), get { parametersOf(lr) }, get()) }
    viewModel { GlLanguageGlViewGlModelGl(get()) }
    viewModel { (lr: GlLauncherGlRegistratorGl) -> GlDialGLViewGLModelGL(get { parametersOf(lr) }) }
    viewModel { GlDialGlActivityGLViewGLModelGL(get()) }
    viewModel { (lr: GlLauncherGlRegistratorGl) -> GlCallGlActivityGlViewGlModelGL(get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: GlLauncherGlRegistratorGl) -> GlCallGlViewGlModelGl(contact, get(), get(), get(), get { parametersOf(lr) }) }
}