package com.vefercal.ler.repository

import com.vefercal.ler.model.theme.ImageTheme
import com.vefercal.ler.model.theme.Theme
import com.vefercal.ler.repository.database.dao.ThemeDao
import com.vefercal.ler.repository.database.entity.ContactTheme
import com.vefercal.ler.repository.database.entity.CustomTheme
import kotlinx.coroutines.flow.MutableSharedFlow

class ThemeRepository(private val themeDao: ThemeDao) {

    val newThemes = MutableSharedFlow<Theme>()

    suspend fun saveNewTheme(pictureFile: String) {
        themeDao.insertCustomTheme(CustomTheme(pictureFile))
        newThemes.emit(ImageTheme(pictureFile))
    }

    fun getCustomThemes() = themeDao.selectCustomThemes().map { it.toImageTheme() }

    fun setContactTheme(contactId: Long, themeFile: String) =
        themeDao.upsertContactTheme(ContactTheme(contactId, themeFile))

    fun getContactTheme(contactId: Long) = themeDao.selectContactThemeForId(contactId)?.theme

}