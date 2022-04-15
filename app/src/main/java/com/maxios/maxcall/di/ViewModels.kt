package com.maxios.maxcall.di

import com.maxios.maxcall.base.LauncherRegistrator
import com.maxios.maxcall.ui.home.HomeViewModel
import com.maxios.maxcall.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModels = module {
    viewModel { MainViewModel(get()) }
    viewModel { (lr: LauncherRegistrator) -> HomeViewModel(get(), get { parametersOf(lr) }, get { parametersOf(lr) }, get()) }
}