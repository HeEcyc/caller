package com.docall.jocall.ui.call.activity

import com.docall.jocall.base.ActivityViewModel
import com.docall.jocall.repository.ContactsRepository
import com.docall.jocall.repository.LocaleRepository
import com.docall.jocall.repository.PermissionRepository
import com.docall.jocall.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)