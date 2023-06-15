package com.docall.jocall.repository

import com.docall.jocall.model.theme.ImageTheme
import com.docall.jocall.model.theme.Theme
import com.docall.jocall.repository.database.dao.ThemeDao
import com.docall.jocall.repository.database.entity.ContactTheme
import com.docall.jocall.repository.database.entity.CustomTheme
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

}