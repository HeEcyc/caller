package com.fantasia.telecaller.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fantasia.telecaller.model.theme.ImageTheme
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}