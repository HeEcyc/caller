package com.maxios.maxcall.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxios.maxcall.model.theme.ImageTheme
import com.maxios.maxcall.model.theme.Theme
import com.maxios.maxcall.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}