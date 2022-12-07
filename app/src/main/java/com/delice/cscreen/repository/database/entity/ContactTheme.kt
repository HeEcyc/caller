package com.delice.cscreen.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.delice.cscreen.model.theme.ImageTheme
import com.delice.cscreen.model.theme.Theme
import com.delice.cscreen.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}