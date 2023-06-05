package com.fusiecal.ler.ui.main

import com.fusiecal.ler.base.ActivityViewModel
import com.fusiecal.ler.repository.LocaleRepository
import com.fusiecal.ler.repository.PermissionRepository
import com.fusiecal.ler.repository.PreferencesRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)