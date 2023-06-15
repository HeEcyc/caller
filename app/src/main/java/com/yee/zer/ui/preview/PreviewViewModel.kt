package com.yee.zer.ui.preview

import com.yee.zer.base.ActivityViewModel
import com.yee.zer.model.theme.Theme
import com.yee.zer.repository.LocaleRepository
import com.yee.zer.repository.call.CallRepository

class PreviewViewModel(
    val theme: Theme,
    val callRepository: CallRepository,
    localeRepository: LocaleRepository,
) : ActivityViewModel(localeRepository)
