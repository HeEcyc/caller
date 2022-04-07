package com.holographic.call.ui.call

import com.holographic.call.base.ActivityViewModel
import com.holographic.call.repository.ContactsRepository
import com.holographic.call.repository.LocaleRepository
import com.holographic.call.repository.PermissionRepository
import com.holographic.call.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)