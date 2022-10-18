package com.fantasia.telecaller.ui.call.activity

import com.fantasia.telecaller.base.FActivityFViewFModelF
import com.fantasia.telecaller.repository.FContactsFRepositoryF
import com.fantasia.telecaller.repository.FLocaleFRepositoryF
import com.fantasia.telecaller.repository.FPermissionFRepositoryF
import com.fantasia.telecaller.repository.call.CallRepository

class FCallFActivityFViewFModelF(
    localeRepository: FLocaleFRepositoryF,
    val contactsRepository: FContactsFRepositoryF,
    val callRepository: CallRepository,
    val permissionRepository: FPermissionFRepositoryF
) : FActivityFViewFModelF(localeRepository)