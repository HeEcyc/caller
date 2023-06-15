package com.docall.jocall.ui.main

import com.docall.jocall.base.ActivityViewModel
import com.docall.jocall.repository.LocaleRepository
import com.docall.jocall.repository.PermissionRepository
import com.docall.jocall.repository.PreferencesRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)