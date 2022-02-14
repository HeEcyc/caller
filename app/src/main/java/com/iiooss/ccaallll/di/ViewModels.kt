package com.iiooss.ccaallll.di

import com.iiooss.ccaallll.base.LauncherRegistrator
import com.iiooss.ccaallll.ui.home.HomeViewModel
import com.iiooss.ccaallll.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { (lr: LauncherRegistrator) -> MainViewModel(get(), get { parametersOf(lr) }) }
    viewModel { (lr: LauncherRegistrator) -> HomeViewModel(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get()) }
}