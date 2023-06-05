package com.fusiecal.ler.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fusiecal.ler.model.theme.ImageTheme
import com.fusiecal.ler.model.theme.Theme
import com.fusiecal.ler.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}