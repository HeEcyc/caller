package com.galaxy.call.ui.call.activity

import com.galaxy.call.base.ActivityViewModel
import com.galaxy.call.repository.ContactsRepository
import com.galaxy.call.repository.LocaleRepository
import com.galaxy.call.repository.PermissionRepository
import com.galaxy.call.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)