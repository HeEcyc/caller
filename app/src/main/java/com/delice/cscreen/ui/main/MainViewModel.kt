package com.delice.cscreen.ui.main

import com.delice.cscreen.base.ActivityViewModel
import com.delice.cscreen.repository.LocaleRepository
import com.delice.cscreen.repository.PermissionRepository
import com.delice.cscreen.repository.PreferencesRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)