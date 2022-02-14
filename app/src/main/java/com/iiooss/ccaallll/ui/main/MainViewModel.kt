package com.iiooss.ccaallll.ui.main

import com.iiooss.ccaallll.base.ActivityViewModel
import com.iiooss.ccaallll.repository.LocaleRepository
import com.iiooss.ccaallll.repository.PermissionRepository

class MainViewModel(
    localeRepository: LocaleRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)