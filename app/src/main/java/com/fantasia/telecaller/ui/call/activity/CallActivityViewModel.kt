package com.fantasia.telecaller.ui.call.activity

import com.fantasia.telecaller.base.ActivityViewModel
import com.fantasia.telecaller.repository.ContactsRepository
import com.fantasia.telecaller.repository.LocaleRepository
import com.fantasia.telecaller.repository.PermissionRepository
import com.fantasia.telecaller.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)