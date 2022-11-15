package com.roobcall.themes.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.roobcall.themes.model.theme.ImageTheme
import com.roobcall.themes.model.theme.Theme
import com.roobcall.themes.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}