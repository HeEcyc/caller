package com.stacky.caller.ui.call.activity

import com.stacky.caller.base.ActivityViewModel
import com.stacky.caller.repository.ContactsRepository
import com.stacky.caller.repository.LocaleRepository
import com.stacky.caller.repository.PermissionRepository
import com.stacky.caller.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)