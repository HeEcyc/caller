package com.maxios.maxcall.ui.call

import com.maxios.maxcall.base.ActivityViewModel
import com.maxios.maxcall.repository.ContactsRepository
import com.maxios.maxcall.repository.LocaleRepository
import com.maxios.maxcall.repository.PermissionRepository
import com.maxios.maxcall.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)
