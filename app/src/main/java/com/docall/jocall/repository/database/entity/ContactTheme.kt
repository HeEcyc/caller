package com.docall.jocall.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.docall.jocall.model.theme.ImageTheme
import com.docall.jocall.model.theme.Theme
import com.docall.jocall.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}