package com.callerafter.lovelycall.ui.main

import com.callerafter.lovelycall.base.ActivityViewModel
import com.callerafter.lovelycall.model.contact.UserContact
import com.callerafter.lovelycall.repository.LocaleRepository
import com.callerafter.lovelycall.repository.PermissionRepository

class MainViewModel(localeRepository: LocaleRepository) : ActivityViewModel(localeRepository) {

    lateinit var permissionRepository: PermissionRepository

    var onContactsSelected: ((List<UserContact>) -> Unit)? = null

}