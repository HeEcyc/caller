package com.galala.lalaxy.ui.call.activity

import com.galala.lalaxy.base.GActivityGViewGModelG
import com.galala.lalaxy.repository.GContactsGRepositoryG
import com.galala.lalaxy.repository.GLocaleGRepositoryG
import com.galala.lalaxy.repository.GPermissionGRepositoryG
import com.galala.lalaxy.repository.call.GCallGRepositoryG

class GCallGActivityGViewGModelG(
    localeRepository: GLocaleGRepositoryG,
    val contactsRepository: GContactsGRepositoryG,
    val callRepository: GCallGRepositoryG,
    val permissionRepository: GPermissionGRepositoryG
) : GActivityGViewGModelG(localeRepository)