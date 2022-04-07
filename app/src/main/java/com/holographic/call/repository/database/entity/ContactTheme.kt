package com.holographic.call.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.holographic.call.model.theme.ImageTheme
import com.holographic.call.model.theme.Theme
import com.holographic.call.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}