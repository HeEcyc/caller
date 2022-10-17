package com.galaxy.call.ui.call.activity

import com.galaxy.call.base.GActivityGViewGModelG
import com.galaxy.call.repository.GContactsGRepositoryG
import com.galaxy.call.repository.GLocaleGRepositoryG
import com.galaxy.call.repository.GPermissionGRepositoryG
import com.galaxy.call.repository.call.GCallGRepositoryG

class GCallGActivityGViewGModelG(
    localeRepository: GLocaleGRepositoryG,
    val contactsRepository: GContactsGRepositoryG,
    val callRepository: GCallGRepositoryG,
    val permissionRepository: GPermissionGRepositoryG
) : GActivityGViewGModelG(localeRepository)