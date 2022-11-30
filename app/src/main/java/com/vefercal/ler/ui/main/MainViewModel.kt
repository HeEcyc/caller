package com.vefercal.ler.ui.main

import com.vefercal.ler.base.ActivityViewModel
import com.vefercal.ler.repository.LocaleRepository
import com.vefercal.ler.repository.PermissionRepository
import com.vefercal.ler.repository.PreferencesRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)