package com.sappy.callthemes.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sappy.callthemes.model.theme.ImageTheme
import com.sappy.callthemes.model.theme.Theme
import com.sappy.callthemes.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}