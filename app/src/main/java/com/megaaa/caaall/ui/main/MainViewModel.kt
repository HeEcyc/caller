package com.megaaa.caaall.ui.main

import com.megaaa.caaall.base.ActivityViewModel
import com.megaaa.caaall.repository.LocaleRepository
import com.megaaa.caaall.repository.PermissionRepository

class MainViewModel(localeRepository: LocaleRepository) : ActivityViewModel(localeRepository) {

    lateinit var permissionRepository: PermissionRepository

}