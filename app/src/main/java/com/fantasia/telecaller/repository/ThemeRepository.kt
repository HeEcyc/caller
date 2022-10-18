package com.fantasia.telecaller.repository

import com.fantasia.telecaller.model.theme.ImageTheme
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.repository.database.dao.ThemeDao
import com.fantasia.telecaller.repository.database.entity.ContactTheme
import com.fantasia.telecaller.repository.database.entity.CustomTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.File

class ThemeRepository(private val themeDao: ThemeDao) {

    val newThemes = MutableSharedFlow<Theme>()
    val deletedThemes = MutableSharedFlow<Theme>()

    suspend fun saveNewTheme(pictureFile: String) {
        themeDao.insertCustomTheme(CustomTheme(pictureFile))
        newThemes.emit(ImageTheme(pictureFile))
    }

    fun getCustomThemes() = themeDao.selectCustomThemes().map { it.toImageTheme() }

    suspend fun deleteCustomTheme(pictureFile: String) {
        File(pictureFile).apply { if (exists()) delete() }
        if (themeDao.deleteCustomThemeCompletely(CustomTheme(pictureFile)))
            deletedThemes.emit(ImageTheme(pictureFile))
    }

    fun setContactTheme(contactId: Long, themeFile: String) =
        themeDao.upsertContactTheme(ContactTheme(contactId, themeFile))

    fun getContactTheme(contactId: Long) = themeDao.selectContactThemeForId(contactId)?.theme

}