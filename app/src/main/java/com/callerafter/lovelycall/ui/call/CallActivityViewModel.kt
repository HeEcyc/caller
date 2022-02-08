package com.callerafter.lovelycall.ui.call

import com.callerafter.lovelycall.base.ActivityViewModel
import com.callerafter.lovelycall.repository.ContactsRepository
import com.callerafter.lovelycall.repository.LocaleRepository
import com.callerafter.lovelycall.repository.PermissionRepository
import com.callerafter.lovelycall.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository
) : ActivityViewModel(localeRepository) {

    lateinit var permissionRepository: PermissionRepository

}