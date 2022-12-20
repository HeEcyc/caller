package com.pecall.poscreen.ui.call.activity

import com.pecall.poscreen.base.ActivityViewModel
import com.pecall.poscreen.repository.ContactsRepository
import com.pecall.poscreen.repository.LocaleRepository
import com.pecall.poscreen.repository.PermissionRepository
import com.pecall.poscreen.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)