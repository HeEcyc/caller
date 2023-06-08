package com.yee.zer.ui.main

import com.yee.zer.base.ActivityViewModel
import com.yee.zer.repository.LocaleRepository
import com.yee.zer.repository.PermissionRepository
import com.yee.zer.repository.PreferencesRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)