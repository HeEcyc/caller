package com.pecall.poscreen.ui.main

import com.pecall.poscreen.base.ActivityViewModel
import com.pecall.poscreen.repository.LocaleRepository
import com.pecall.poscreen.repository.PermissionRepository
import com.pecall.poscreen.repository.PreferencesRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)