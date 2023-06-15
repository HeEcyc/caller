package com.docall.jocall.ui.preview

import com.docall.jocall.base.ActivityViewModel
import com.docall.jocall.model.theme.Theme
import com.docall.jocall.repository.LocaleRepository
import com.docall.jocall.repository.PreferencesRepository
import com.docall.jocall.repository.call.CallRepository

class PreviewViewModel(
    val theme: Theme,
    val callRepository: CallRepository,
    localeRepository: LocaleRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)