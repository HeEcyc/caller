package com.yee.zer.ui.call.activity

import com.yee.zer.base.ActivityViewModel
import com.yee.zer.repository.ContactsRepository
import com.yee.zer.repository.LocaleRepository
import com.yee.zer.repository.PermissionRepository
import com.yee.zer.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)