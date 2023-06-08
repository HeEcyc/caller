package com.yee.zer.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yee.zer.model.theme.ImageTheme
import com.yee.zer.model.theme.Theme
import com.yee.zer.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}