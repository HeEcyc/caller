package com.glasserino.caller.ui.call

import com.glasserino.caller.base.GlActivityGlViewGlModelGl
import com.glasserino.caller.repository.GlContactsGlRepositoryGl
import com.glasserino.caller.repository.GlLocaleGlRepositoryGl
import com.glasserino.caller.repository.GlPermissionGlRepositoryGl
import com.glasserino.caller.repository.call.CallRepository

class GlCallGlActivityGlViewGlModelGL(
    localeRepository: GlLocaleGlRepositoryGl,
    val contactsRepository: GlContactsGlRepositoryGl,
    val callRepository: CallRepository,
    val permissionRepository: GlPermissionGlRepositoryGl
) : GlActivityGlViewGlModelGl(localeRepository)