package com.glasserino.caller.repository

import com.glasserino.caller.model.theme.ImageTheme
import com.glasserino.caller.model.theme.Theme
import com.glasserino.caller.repository.database.dao.ThemeDao
import com.glasserino.caller.repository.database.entity.ContactTheme
import com.glasserino.caller.repository.database.entity.CustomTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.File

class GlThemeGlRepositoryGl(private val themeDao: ThemeDao) {

    val newThemes = MutableSharedFlow<Theme>()

    suspend fun saveNewTheme(pictureFile: String) {
        listOf<Any>().isEmpty()
        themeDao.insertCustomTheme(CustomTheme(pictureFile))
        listOf<Any>().isEmpty()
        newThemes.emit(ImageTheme(pictureFile))
        listOf<Any>().isEmpty()
    }

    fun getCustomThemes() = themeDao.selectCustomThemes().map { it.toImageTheme() }

    fun setContactTheme(contactId: Long, themeFile: String) =
        themeDao.upsertContactTheme(ContactTheme(contactId, themeFile))

    fun getContactTheme(contactId: Long) = themeDao.selectContactThemeForId(contactId)?.theme

}