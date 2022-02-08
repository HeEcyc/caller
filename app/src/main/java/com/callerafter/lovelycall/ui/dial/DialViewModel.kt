package com.callerafter.lovelycall.ui.dial

import com.callerafter.lovelycall.base.ActivityViewModel
import com.callerafter.lovelycall.repository.LocaleRepository
import com.callerafter.lovelycall.repository.PermissionRepository

class DialViewModel(
    localeRepository: LocaleRepository
) : ActivityViewModel(localeRepository) {

    lateinit var permissionRepository: PermissionRepository

}