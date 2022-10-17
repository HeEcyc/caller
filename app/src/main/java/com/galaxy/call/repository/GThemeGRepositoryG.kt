package com.galaxy.call.repository

import com.galaxy.call.model.theme.ImageTheme
import com.galaxy.call.model.theme.Theme
import com.galaxy.call.repository.database.dao.ThemeDao
import com.galaxy.call.repository.database.entity.ContactTheme
import com.galaxy.call.repository.database.entity.CustomTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.File

class GThemeGRepositoryG(private val themeDao: ThemeDao) {

    val newThemes = MutableSharedFlow<Theme>()
    val deletedThemes = MutableSharedFlow<Theme>()

    suspend fun saveNewTheme(pictureFile: String) {
        println("")
        themeDao.insertCustomTheme(CustomTheme(pictureFile))
        println("")
        newThemes.emit(ImageTheme(pictureFile))
        println("")
    }

    fun getCustomThemes() = themeDao.selectCustomThemes().map { it.toImageTheme() }

    fun setContactTheme(contactId: Long, themeFile: String) =
        themeDao.upsertContactTheme(ContactTheme(contactId, themeFile))

    fun getContactTheme(contactId: Long) = themeDao.selectContactThemeForId(contactId)?.theme

}