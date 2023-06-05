package com.fusiecal.ler.ui.call.activity

import com.fusiecal.ler.base.ActivityViewModel
import com.fusiecal.ler.repository.ContactsRepository
import com.fusiecal.ler.repository.LocaleRepository
import com.fusiecal.ler.repository.PermissionRepository
import com.fusiecal.ler.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)