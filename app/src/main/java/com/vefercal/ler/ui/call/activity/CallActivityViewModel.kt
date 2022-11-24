package com.vefercal.ler.ui.call.activity

import com.vefercal.ler.base.ActivityViewModel
import com.vefercal.ler.repository.ContactsRepository
import com.vefercal.ler.repository.LocaleRepository
import com.vefercal.ler.repository.PermissionRepository
import com.vefercal.ler.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)