package com.delice.cscreen.repository

import com.delice.cscreen.model.theme.ImageTheme
import com.delice.cscreen.model.theme.Theme
import com.delice.cscreen.repository.database.dao.ThemeDao
import com.delice.cscreen.repository.database.entity.ContactTheme
import com.delice.cscreen.repository.database.entity.CustomTheme
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