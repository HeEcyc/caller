package com.iiooss.ccaallll.ui.call

import com.iiooss.ccaallll.base.ActivityViewModel
import com.iiooss.ccaallll.repository.ContactsRepository
import com.iiooss.ccaallll.repository.LocaleRepository
import com.iiooss.ccaallll.repository.PermissionRepository
import com.iiooss.ccaallll.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)