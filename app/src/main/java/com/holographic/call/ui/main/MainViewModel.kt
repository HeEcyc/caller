package com.holographic.call.ui.main

import com.holographic.call.base.ActivityViewModel
import com.holographic.call.repository.LocaleRepository
import com.holographic.call.repository.PermissionRepository
import com.holographic.call.repository.PreferencesRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)