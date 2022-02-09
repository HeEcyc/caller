package com.megaaa.caaall.ui.dial

import com.megaaa.caaall.base.ActivityViewModel
import com.megaaa.caaall.repository.LocaleRepository
import com.megaaa.caaall.repository.PermissionRepository

class DialViewModel(
    localeRepository: LocaleRepository
) : ActivityViewModel(localeRepository) {

    lateinit var permissionRepository: PermissionRepository

}