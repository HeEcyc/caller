package com.fantasia.telecaller.di

import com.fantasia.telecaller.base.FLauncherFRegistratorF
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.ui.call.activity.FCallFActivityFViewFModelF
import com.fantasia.telecaller.ui.call.fragment.FCallFViewFModelF
import com.fantasia.telecaller.ui.contact.FContactFViewFModelF
import com.fantasia.telecaller.ui.contacts.FContactsFFragmentF
import com.fantasia.telecaller.ui.contacts.FContactsFViewFModelF
import com.fantasia.telecaller.ui.dial.activity.FDialFActivityFViewFModelF
import com.fantasia.telecaller.ui.dial.fragment.FDialFViewFModelF
import com.fantasia.telecaller.ui.home.FHomeFViewFModelF
import com.fantasia.telecaller.ui.language.FLanguageFViewFModelF
import com.fantasia.telecaller.ui.main.FMainFViewFModelF
import com.fantasia.telecaller.ui.preview.FPreviewFViewFModelF
import com.fantasia.telecaller.ui.settings.FSettingsFViewFModelF
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: FLauncherFRegistratorF) -> FMainFViewFModelF(get(), get { parametersOf(lr) }, get()) }
    viewModel { (lr: FLauncherFRegistratorF) -> FHomeFViewFModelF(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get()) }
    viewModel { (lr: FLauncherFRegistratorF) -> FSettingsFViewFModelF(get(), get { parametersOf(lr) }, get()) }
    viewModel { FLanguageFViewFModelF(get()) }
    viewModel { (mode: FContactsFFragmentF.Mode, lr: FLauncherFRegistratorF) -> FContactsFViewFModelF(mode, get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: FLauncherFRegistratorF) -> FContactFViewFModelF(contact, get { parametersOf(lr) }, get()) }
    viewModel { (t: Theme) -> FPreviewFViewFModelF(t, get(), get(), get()) }
    viewModel { (lr: FLauncherFRegistratorF) -> FDialFViewFModelF(get { parametersOf(lr) }) }
    viewModel { FDialFActivityFViewFModelF(get()) }
    viewModel { (lr: FLauncherFRegistratorF) -> FCallFActivityFViewFModelF(get(), get(), get(), get { parametersOf(lr) }) }
    viewModel { (contact: UserContact, lr: FLauncherFRegistratorF) -> FCallFViewFModelF(contact, get(), get(), get(), get { parametersOf(lr) }) }

}
