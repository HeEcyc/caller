package com.glass.call.ui.call

import com.glass.call.base.ActivityViewModel
import com.glass.call.repository.ContactsRepository
import com.glass.call.repository.LocaleRepository
import com.glass.call.repository.PermissionRepository
import com.glass.call.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)