package com.galala.lalaxy.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.galala.lalaxy.model.theme.ImageTheme
import com.galala.lalaxy.model.theme.Theme
import com.galala.lalaxy.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}