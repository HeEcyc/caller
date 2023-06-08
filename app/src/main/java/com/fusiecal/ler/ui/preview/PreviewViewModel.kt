package com.fusiecal.ler.ui.preview

import com.fusiecal.ler.base.ActivityViewModel
import com.fusiecal.ler.model.theme.Theme
import com.fusiecal.ler.repository.LocaleRepository
import com.fusiecal.ler.repository.PreferencesRepository
import com.fusiecal.ler.repository.call.CallRepository

class PreviewViewModel(
    val theme: Theme,
    val callRepository: CallRepository,
    localeRepository: LocaleRepository,
    val preferencesRepository: PreferencesRepository
) : ActivityViewModel(localeRepository)