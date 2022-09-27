package com.sappy.callthemes.ui.call.activity

import com.sappy.callthemes.base.ActivityViewModel
import com.sappy.callthemes.repository.ContactsRepository
import com.sappy.callthemes.repository.LocaleRepository
import com.sappy.callthemes.repository.PermissionRepository
import com.sappy.callthemes.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)