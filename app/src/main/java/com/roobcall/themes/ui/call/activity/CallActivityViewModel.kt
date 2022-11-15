package com.roobcall.themes.ui.call.activity

import com.roobcall.themes.base.ActivityViewModel
import com.roobcall.themes.repository.ContactsRepository
import com.roobcall.themes.repository.LocaleRepository
import com.roobcall.themes.repository.PermissionRepository
import com.roobcall.themes.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)