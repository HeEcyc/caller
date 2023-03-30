package com.bbbotttixxx.callscreen.ui.main

import com.bbbotttixxx.callscreen.base.ActivityViewModel
import com.bbbotttixxx.callscreen.repository.LocaleRepository
import com.bbbotttixxx.callscreen.repository.PermissionRepository
import com.bbbotttixxx.callscreen.repository.PreferencesRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)