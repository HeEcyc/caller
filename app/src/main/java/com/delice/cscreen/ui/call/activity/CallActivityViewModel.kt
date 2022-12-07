package com.delice.cscreen.ui.call.activity

import com.delice.cscreen.base.ActivityViewModel
import com.delice.cscreen.repository.ContactsRepository
import com.delice.cscreen.repository.LocaleRepository
import com.delice.cscreen.repository.PermissionRepository
import com.delice.cscreen.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)