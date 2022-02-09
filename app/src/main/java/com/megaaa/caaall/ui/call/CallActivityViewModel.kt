package com.megaaa.caaall.ui.call

import com.megaaa.caaall.base.ActivityViewModel
import com.megaaa.caaall.repository.ContactsRepository
import com.megaaa.caaall.repository.LocaleRepository
import com.megaaa.caaall.repository.PermissionRepository
import com.megaaa.caaall.repository.call.CallRepository

class CallActivityViewModel(
    localeRepository: LocaleRepository,
    val contactsRepository: ContactsRepository,
    val callRepository: CallRepository
) : ActivityViewModel(localeRepository) {

    lateinit var permissionRepository: PermissionRepository

}