package com.fantasy.call.ui.call.activity

import com.fantasy.call.base.ActivityViewModel
import com.fantasy.call.repository.ContactsRepository
import com.fantasy.call.repository.LocaleRepository
import com.fantasy.call.repository.PermissionRepository
import com.fantasy.call.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)