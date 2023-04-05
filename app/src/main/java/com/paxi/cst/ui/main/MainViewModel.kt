package com.paxi.cst.ui.main

import com.paxi.cst.base.ActivityViewModel
import com.paxi.cst.repository.LocaleRepository
import com.paxi.cst.repository.PermissionRepository
import com.paxi.cst.repository.PreferencesRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)