package com.paxi.cst.ui.call.activity

import com.paxi.cst.base.ActivityViewModel
import com.paxi.cst.repository.ContactsRepository
import com.paxi.cst.repository.LocaleRepository
import com.paxi.cst.repository.PermissionRepository
import com.paxi.cst.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)