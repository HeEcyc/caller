package com.threed.caller.ui.call.activity

import com.threed.caller.base.ActivityViewModel
import com.threed.caller.repository.ContactsRepository
import com.threed.caller.repository.LocaleRepository
import com.threed.caller.repository.PermissionRepository
import com.threed.caller.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)