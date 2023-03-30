package com.bbbotttixxx.callscreen.ui.call.activity

import com.bbbotttixxx.callscreen.base.ActivityViewModel
import com.bbbotttixxx.callscreen.repository.ContactsRepository
import com.bbbotttixxx.callscreen.repository.LocaleRepository
import com.bbbotttixxx.callscreen.repository.PermissionRepository
import com.bbbotttixxx.callscreen.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository,
    val permissionRepository: PermissionRepository
) : ActivityViewModel(localeRepository)