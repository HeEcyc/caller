package com.vefercal.ler.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vefercal.ler.model.theme.ImageTheme
import com.vefercal.ler.model.theme.Theme
import com.vefercal.ler.utils.presetThemes

@Entity(tableName = "contacts_themes")
data class ContactTheme(
    @PrimaryKey
    val contactId: Long,
    val themeFile: String
) {

    val theme: Theme get() = presetThemes
        .firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile)

}