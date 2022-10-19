package com.glasserino.caller.ui.call

import com.glasserino.caller.base.ActivityViewModel
import com.glasserino.caller.repository.ContactsRepository
import com.glasserino.caller.repository.LocaleRepository
import com.glasserino.caller.repository.PermissionRepository
import com.glasserino.caller.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)