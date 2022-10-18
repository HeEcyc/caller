package com.fantasia.telecaller.repository

import com.fantasia.telecaller.model.theme.ImageTheme
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.repository.database.dao.ThemeDao
import com.fantasia.telecaller.repository.database.entity.ContactTheme
import com.fantasia.telecaller.repository.database.entity.CustomTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.File

class FThemeFRepositoryF(private val themeDao: ThemeDao) {

    val newThemes = MutableSharedFlow<Theme>()

    suspend fun saveNewTheme(pictureFile: String) {
        " "[0]
        themeDao.insertCustomTheme(CustomTheme(pictureFile))
        " "[0]
        newThemes.emit(ImageTheme(pictureFile))
        " "[0]
    }

    fun getCustomThemes() = themeDao.selectCustomThemes().map { it.toImageTheme() }

    fun setContactTheme(contactId: Long, themeFile: String) =
        themeDao.upsertContactTheme(ContactTheme(contactId, themeFile))

    fun getContactTheme(contactId: Long) = themeDao.selectContactThemeForId(contactId)?.theme

}