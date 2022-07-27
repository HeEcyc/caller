package com.yellowmood.caller.ui.call.activity

import com.yellowmood.caller.base.ActivityViewModel
import com.yellowmood.caller.repository.ContactsRepository
import com.yellowmood.caller.repository.LocaleRepository
import com.yellowmood.caller.repository.PermissionRepository
import com.yellowmood.caller.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)