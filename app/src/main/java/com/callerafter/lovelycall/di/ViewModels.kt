package com.callerafter.lovelycall.di

import com.callerafter.lovelycall.ui.home.HomeFragment
import com.callerafter.lovelycall.ui.home.HomeViewModel
import com.callerafter.lovelycall.ui.main.MainViewModel
import com.callerafter.lovelycall.ui.settings.SettingsViewModel
import com.callerafter.lovelycall.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { SplashViewModel() }
    viewModel { MainViewModel() }
    viewModel { (mode: HomeFragment.Mode) -> HomeViewModel(mode, get()) }
    viewModel { SettingsViewModel(get()) }
}