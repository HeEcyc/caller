package com.iiooss.ccaallll.ui.main

import com.iiooss.ccaallll.base.ActivityViewModel
import com.iiooss.ccaallll.repository.LocaleRepository
import com.iiooss.ccaallll.repository.PermissionRepository
import com.iiooss.ccaallll.repository.PreferencesRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)