package com.galala.lalaxy.repository

import com.galala.lalaxy.model.theme.ImageTheme
import com.galala.lalaxy.model.theme.Theme
import com.galala.lalaxy.repository.database.dao.ThemeDao
import com.galala.lalaxy.repository.database.entity.ContactTheme
import com.galala.lalaxy.repository.database.entity.CustomTheme
import kotlinx.coroutines.flow.MutableSharedFlow

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