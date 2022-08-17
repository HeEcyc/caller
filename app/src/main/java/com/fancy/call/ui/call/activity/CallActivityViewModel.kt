package com.fancy.call.ui.call.activity

import com.fancy.call.base.ActivityViewModel
import com.fancy.call.repository.ContactsRepository
import com.fancy.call.repository.LocaleRepository
import com.fancy.call.repository.PermissionRepository
import com.fancy.call.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)